package ru.example.department.service.base;

import javax.servlet.http.HttpSession;

public interface EntityDeleteService<ID>  {
    void delete(ID id, HttpSession session);
}
