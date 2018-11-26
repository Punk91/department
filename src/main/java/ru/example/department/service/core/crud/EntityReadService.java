package ru.example.department.service.core.crud;

import ru.example.department.model.core.QueryResult;
import ru.example.department.model.core.ReadRequest;

import java.util.List;

public interface EntityReadService<ENTITY, ID> {
    QueryResult<ENTITY> findAllByParams(ReadRequest readRequest);
    List<ENTITY> findAll();
    ENTITY findById(ID id);
}
