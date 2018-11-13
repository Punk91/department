package ru.example.department.service.core.crud;

import ru.example.department.repository.core.BaseRepository;

import javax.servlet.http.HttpSession;

public class EntityDeleteServiceBase<ENTITY, ID> implements EntityDeleteService<ID> {

    private BaseRepository<ENTITY, ID> repository;

    public EntityDeleteServiceBase(BaseRepository<ENTITY, ID> repository) {
        this.repository = repository;
    }

    @Override
    public void delete(ID id, HttpSession session) {
        repository.deleteById(id);
    }
}
