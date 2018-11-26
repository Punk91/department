package ru.example.department.service.core.crud;

import ru.example.department.model.core.QueryResult;
import ru.example.department.model.core.ReadRequest;
import ru.example.department.repository.core.BaseRepository;

import java.util.List;

public class EntityReadServiceBase<ENTITY, ID>  implements EntityReadService<ENTITY, ID>  {

    private BaseRepository<ENTITY, ID> repository;

    public EntityReadServiceBase(BaseRepository<ENTITY, ID> repository) {
        this.repository = repository;
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
        return repository.getOne(id);
    }
}
