package ru.example.department.service.core.crud;

import ru.example.department.model.core.dto.QueryResult;
import ru.example.department.model.core.dto.ReadRequest;

import java.util.List;

public interface EntityReadService<ENTITY, ID> {
    QueryResult<ENTITY> findAllByParams(ReadRequest readRequest);
    List<ENTITY> findAll();
    ENTITY findById(ID id);
}
