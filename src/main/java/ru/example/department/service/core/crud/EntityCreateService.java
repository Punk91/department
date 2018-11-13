package ru.example.department.service.core.crud;

import javax.servlet.http.HttpSession;

public interface EntityCreateService<ENTITY, DTO> {
    ENTITY create (DTO entity, HttpSession session);
}
