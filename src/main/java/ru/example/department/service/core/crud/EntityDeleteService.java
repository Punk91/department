package ru.example.department.service.core.crud;

import javax.servlet.http.HttpSession;

public interface EntityDeleteService<DTO, ID>  {
    void delete(ID id, HttpSession session);
}
