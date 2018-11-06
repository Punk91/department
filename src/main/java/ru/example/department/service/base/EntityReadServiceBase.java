package ru.example.department.service.base;

import ru.example.department.model.dto.base.QueryResult;
import ru.example.department.repository.BaseRepository;

import java.util.List;

public class EntityReadServiceBase<ENTITY, ID>  implements EntityReadService<ENTITY, ID>  {

    private BaseRepository<ENTITY, ID> repository;

    public EntityReadServiceBase(BaseRepository<ENTITY, ID> repository) {
        this.repository = repository;
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
}
