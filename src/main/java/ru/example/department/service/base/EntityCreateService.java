package ru.example.department.service.base;

import javax.servlet.http.HttpSession;

public interface EntityCreateService<ENTITY> {
    ENTITY create (ENTITY entity, HttpSession session);
}
