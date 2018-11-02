package ru.example.department.controller.base;

import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.example.department.model.dto.base.*;
import ru.example.department.service.base.ObjectMapperService;
import ru.example.department.service.base.ReadOnlyCRUDService;
import ru.example.department.service.converter.base.DtoEntityConverter;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

public abstract class BaseRestController<DTO, ENTITY, ID extends Serializable
        , CRUD_SERVICE extends ReadOnlyCRUDService<ENTITY, ID>
        , CONVERTER extends DtoEntityConverter<DTO, ENTITY>> {

    @Autowired
    protected ObjectMapperService objectMapperService;

    protected CRUD_SERVICE crudService;
    protected CONVERTER dtoEntityConverter;

    protected abstract CRUD_SERVICE obtainCrudService();
    protected abstract CONVERTER obtainDtoConverter();

    @PostConstruct
    public void init() {
        crudService = obtainCrudService();
        dtoEntityConverter = obtainDtoConverter();
    }

    protected abstract RestResponse<DTO> doCreate(DTO dto, HttpSession session, HttpServletRequest httpServletRequest);

    protected abstract RestResponse<DTO> doUpdate(DTO dto, ID id, HttpSession session, HttpServletRequest httpServletRequest);

    protected abstract RestResponse<DTO> doDelete(ID id, HttpSession session, HttpServletRequest httpServletRequest);

    @RequestMapping(value = "create/{id}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE
            , consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @Transactional
    @ResponseBody
    public RestResponse<DTO> create(@RequestBody DTO dto, HttpSession session, HttpServletRequest httpServletRequest) {
        return doCreate(dto, session, httpServletRequest);
    }

    @RequestMapping(value = "update/{id}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE
            , consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @Transactional
    @ResponseBody
    public RestResponse<DTO> update(@RequestBody DTO dto, @PathVariable ID id, HttpSession session
            , HttpServletRequest httpServletRequest) {
        return doUpdate(dto, id, session, httpServletRequest);
    }

    @RequestMapping(value = "delete/{id}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE
            , consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @Transactional
    @ResponseBody
    public RestResponse<DTO> delete(@PathVariable ID id, HttpSession session, HttpServletRequest httpServletRequest) {
        return doDelete(id, session, httpServletRequest);
    }

    @RequestMapping(value = "read", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @Transactional
    @ResponseBody
    public RestResponse<DTO> read(@RequestParam(value = "page") Integer page,
                                  @RequestParam(value = "size") Integer size,
                                  @RequestParam(value = "start", required = false) Integer start,
                                  @RequestParam(value = "limit", required = false) Integer limit,
                                  @RequestParam(value = "sort", required = false) String sort,
                                  @RequestParam(value = "filter", required = false) String filter) throws IOException {

        List<SortData> sortList = null;
        if (sort != null) {
            sortList = objectMapperService.readValue(sort, new TypeReference<List<SortData>>() {
            });
        }
        List<Filter> filterList = null;
        if (filter != null) {
            filterList = objectMapperService.readValue(filter, new TypeReference<List<Filter>>() {
            });
        }

        ReadRequest readRequest = new ReadRequest(page, size, start, limit, sortList, filterList);

        QueryResult<ENTITY> queryResult = crudService.getByParams(readRequest);
        List<DTO> dtoList = dtoEntityConverter.entityListToDtoList(queryResult.getList());

        return new RestResponse<>(true, queryResult.getTotalCount(), dtoList);

    }

}