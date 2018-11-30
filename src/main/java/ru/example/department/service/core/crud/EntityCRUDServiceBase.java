package ru.example.department.service.core.crud;

import lombok.extern.slf4j.Slf4j;
import ru.example.department.model.core.dto.QueryResult;
import ru.example.department.model.core.dto.ReadRequest;
import ru.example.department.repository.core.BaseRepository;
import ru.example.department.service.core.converter.DtoEntityConverter;
import ru.example.department.service.core.log.LogChanges;
import ru.example.department.util.CRUDException;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;
import java.util.List;

@Slf4j
public abstract class EntityCRUDServiceBase<ENTITY, DTO, ID> implements EntityCRUDService<ENTITY, ID, DTO>  {

    private EntityReadService<ENTITY, ID> readService;
    private EntityCreateService<ENTITY, DTO> createService;
    private EntityUpdateService<ENTITY, DTO, ID> updateService;
    private EntityDeleteService<DTO, ID> deleteService;
    private BaseRepository<ENTITY, ID> repository;
    private DtoEntityConverter<DTO, ENTITY> dtoEntityConverter;
    private LogChanges logChanges;
    private String entityName;

    public EntityCRUDServiceBase(BaseRepository<ENTITY, ID> repository, DtoEntityConverter<DTO, ENTITY> dtoEntityConverter, LogChanges logChanges, String entityName) {
        this.repository = repository;
        this.dtoEntityConverter = dtoEntityConverter;
        this.entityName = entityName;
        this.logChanges = logChanges;
    }

    @PostConstruct
    public void init() {
        createService = new EntityCreateServiceBase<>(repository, dtoEntityConverter, logChanges, entityName);
        readService = new EntityReadServiceBase<>(repository);
        updateService = new EntityUpdateServiceBase<>(repository, dtoEntityConverter, logChanges, entityName);
        deleteService = new EntityDeleteServiceBase<>(repository, dtoEntityConverter, logChanges, entityName);
    }



    @Override
    public DTO createFromDto(DTO dto, HttpSession session) throws CRUDException {
        return createService.createFromDto(dto, session);
    }

    @Override
    public void delete(ID id, HttpSession session) {
        deleteService.delete(id, session);
    }

    @Override
    public QueryResult<ENTITY> findAllByParams(ReadRequest readRequest) {
        return null;
    }

    @Override
    public List<ENTITY> findAll() {
        return null;
    }

    @Override
    public ENTITY findById(ID id) {
        return null;
    }

    @Override
    public DTO updateFromDto(DTO dto, ID id, HttpSession session) throws CRUDException {
        return updateService.updateFromDto(dto, id, session);
    }




    public void setReadService(EntityReadService<ENTITY, ID> readService) {
        this.readService = readService;
    }
    public void setCreateService(EntityCreateService<ENTITY, DTO> createService) {
        this.createService = createService;
    }
    public void setUpdateService(EntityUpdateService<ENTITY, DTO, ID> updateService) {
        this.updateService = updateService;
    }
    public void setDeleteService(EntityDeleteService<DTO, ID> deleteService) {
        this.deleteService = deleteService;
    }

}
