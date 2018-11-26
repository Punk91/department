package ru.example.department.service.core.crud;

import ru.example.department.util.CRUDException;

import javax.servlet.http.HttpSession;

public interface EntityUpdateService<ENTITY, DTO, ID> {
    DTO updateFromDto(DTO dto, ID id, HttpSession session) throws CRUDException;
}
