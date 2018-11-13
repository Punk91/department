package ru.example.department.service.core.crud;

import javax.servlet.http.HttpSession;

public interface EntityUpdateService<ENTITY> {
    ENTITY update(ENTITY object, HttpSession session);
}
