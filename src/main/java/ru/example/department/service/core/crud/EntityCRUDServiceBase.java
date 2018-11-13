package ru.example.department.service.core.crud;

import ru.example.department.model.core.QueryResult;
import ru.example.department.repository.core.BaseRepository;
import ru.example.department.service.core.converter.DtoEntityConverter;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;
import java.util.List;

public abstract class EntityCRUDServiceBase<ENTITY, DTO, ID> implements EntityCRUDService<ENTITY, ID, DTO>  {

    private EntityReadService<ENTITY, ID> readService;
    private EntityCreateService<ENTITY, DTO> createService;
    private EntityUpdateService<ENTITY> updateService;
    private EntityDeleteService<ID> deleteService;
    private BaseRepository<ENTITY, ID> repository;
    private DtoEntityConverter<DTO, ENTITY> dtoEntityConverter;
    private String entityName;

    public EntityCRUDServiceBase(BaseRepository<ENTITY, ID> repository, DtoEntityConverter<DTO, ENTITY> dtoEntityConverter, String entityName) {
        this.repository = repository;
        this.dtoEntityConverter = dtoEntityConverter;
        this.entityName = entityName;
    }

    @PostConstruct
    public void init() {
        readService = new EntityReadServiceBase<>(repository);
        createService = new EntityCreateServiceBase<>(repository, dtoEntityConverter, entityName);
        deleteService = new EntityDeleteServiceBase<>(repository);
    }

    @Override
    public ENTITY create(DTO dto, HttpSession session) {
        return createService.create(dto, session);
    }

    @Override
    public void delete(ID id, HttpSession session) {
    }

    @Override
    public QueryResult<ENTITY> findAllByParams() {
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
    public ENTITY update(ENTITY object, HttpSession session) {
        return null;
    }


    public void setReadService(EntityReadService<ENTITY, ID> readService) {
        this.readService = readService;
    }
    public void setCreateService(EntityCreateService<ENTITY, DTO> createService) {
        this.createService = createService;
    }
    public void setUpdateService(EntityUpdateService<ENTITY> updateService) {
        this.updateService = updateService;
    }
    public void setDeleteService(EntityDeleteService<ID> deleteService) {
        this.deleteService = deleteService;
    }

}
