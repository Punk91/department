package ru.example.department.service.core.crud;

import ru.example.department.util.CRUDException;

import javax.servlet.http.HttpSession;

public interface EntityCreateService<ENTITY, DTO> {
    DTO createFromDto(DTO dto, HttpSession session) throws CRUDException;
}
