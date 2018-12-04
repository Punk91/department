package ru.example.department.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.example.department.model.core.dto.Filter;
import ru.example.department.model.core.dto.QueryResult;
import ru.example.department.model.core.dto.ReadRequest;
import ru.example.department.model.core.dto.SortData;
import ru.example.department.model.dto.TestDto;
import ru.example.department.model.core.api.RestResponse;
import ru.example.department.model.entity.TestEntity;
import ru.example.department.service.TestService;
import ru.example.department.service.converter.TestDtoEntityConverter;
import ru.example.department.util.CRUDException;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/test/")
public class TestController {

    @Autowired
    private TestService service;
    @Autowired
    private TestDtoEntityConverter converter;
    @Autowired
    private ObjectMapper objectMapper;


    @PostMapping
    public RestResponse<TestDto> create(@RequestBody TestDto dto, HttpSession session) {
        TestDto testDto = null;
        try {
            testDto = service.createFromDto(dto, session);
        } catch (CRUDException e) {
            e.printStackTrace();
        }
        return new RestResponse<>(testDto);
    }

    @GetMapping
    public ResponseEntity getTest (@RequestParam(value = "page") Integer page,
                                   @RequestParam(value = "size") Integer size,
                                   @RequestParam(value = "sort", required = false) String sort,
                                   @RequestParam(value = "filter", required = false) String filter) throws IOException {
        List<SortData> sortList = null;
        if (sort != null) {
            sortList = objectMapper.readValue(sort, new TypeReference<List<SortData>>() {
            });
        }
        List<Filter> filterList = null;
        if (filter != null) {
            filterList = objectMapper.readValue(filter, new TypeReference<List<Filter>>() {
            });
        }

        ReadRequest readRequest = new ReadRequest(page, size, sortList, filterList);

        QueryResult<TestEntity> queryResult = service.findAllByParams(readRequest);
        List<TestDto> dtoList = converter.entityListToDtoList(queryResult.getList());
        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }

}
