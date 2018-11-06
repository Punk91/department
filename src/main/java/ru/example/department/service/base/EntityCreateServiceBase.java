package ru.example.department.service.base;

import ru.example.department.repository.BaseRepository;
import javax.servlet.http.HttpSession;


public class EntityCreateServiceBase<ENTITY, ID> implements EntityCreateService<ENTITY> {

    private BaseRepository<ENTITY, ID> repository;

    public EntityCreateServiceBase(BaseRepository<ENTITY, ID> repository) {
        this.repository = repository;
    }

    @Override
    public ENTITY create(ENTITY entity, HttpSession session) {
        return repository.saveAndFlush(entity);
    }
}
