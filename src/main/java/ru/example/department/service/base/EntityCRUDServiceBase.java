package ru.example.department.service.base;

import ru.example.department.model.dto.base.QueryResult;
import ru.example.department.repository.BaseRepository;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;
import java.util.List;

public abstract class EntityCRUDServiceBase<ENTITY, ID> implements EntityCRUDService<ENTITY, ID>  {

    private EntityReadService<ENTITY, ID> readService;
    private EntityCreateService<ENTITY> createService;
    private EntityUpdateService<ENTITY> updateService;
    private EntityDeleteService<ID> deleteService;
    private BaseRepository<ENTITY, ID> repository;

    public EntityCRUDServiceBase(BaseRepository<ENTITY, ID> repository) {
        this.repository = repository;
    }

    @PostConstruct
    public void init() {
        readService = new EntityReadServiceBase<>(repository);
        createService = new EntityCreateServiceBase<>(repository);
        deleteService = new EntityDeleteServiceBase<>(repository);
    }

    @Override
    public ENTITY create(ENTITY entity, HttpSession session) {
        return createService.create(entity, session);
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
    public void setCreateService(EntityCreateService<ENTITY> createService) {
        this.createService = createService;
    }
    public void setUpdateService(EntityUpdateService<ENTITY> updateService) {
        this.updateService = updateService;
    }
    public void setDeleteService(EntityDeleteService<ID> deleteService) {
        this.deleteService = deleteService;
    }

}
