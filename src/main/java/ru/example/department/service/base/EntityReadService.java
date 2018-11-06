package ru.example.department.service.base;

import ru.example.department.model.dto.base.QueryResult;

import java.util.List;

public interface EntityReadService<ENTITY, ID> {
    QueryResult<ENTITY> findAllByParams();
    List<ENTITY> findAll();
    ENTITY findById(ID id);
}
