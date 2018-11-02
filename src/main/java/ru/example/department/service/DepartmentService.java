package ru.example.department.service;

import ru.example.department.model.entity.DepartmentEntity;
import ru.example.department.util.NoEntryException;

import java.util.List;
import java.util.UUID;

public interface DepartmentService {

    DepartmentEntity findById(UUID id) throws NoEntryException;

    DepartmentEntity findByName(String name);

    void saveDepartment(DepartmentEntity department);

    void updateDepartment(DepartmentEntity department, DepartmentEntity currentDepartment);

    void deleteDepartmentById(UUID id) throws NoEntryException;

    void deleteAllDepartments();

    List<DepartmentEntity> findAllDepartments();

    boolean isDepartmentExist(DepartmentEntity department);

}
