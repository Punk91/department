package ru.example.department.service.core.crud;

import ru.example.department.model.core.QueryResult;

import java.util.List;

public interface EntityReadService<ENTITY, ID> {
    QueryResult<ENTITY> findAllByParams();
    List<ENTITY> findAll();
    ENTITY findById(ID id);
}
