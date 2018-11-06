package ru.example.department.service.base;

import javax.servlet.http.HttpSession;

public interface EntityUpdateService<ENTITY> {
    ENTITY update(ENTITY object, HttpSession session);
}
