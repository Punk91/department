package ru.example.department.service.base;

import ru.example.department.model.dto.base.QueryResult;
import ru.example.department.model.dto.base.ReadRequest;

import java.io.Serializable;
import java.util.List;

/**
 * @since 21.04.16
 */
public interface ReadOnlyCRUDService<ENTITY, ID extends Serializable> {

    String getEntityName();

    List<ENTITY> findAll();

    ENTITY get(ID id);

    QueryResult<ENTITY> getByParams(ReadRequest readRequest);

}
