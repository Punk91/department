package ru.example.department.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.example.department.model.DepartmentEntity;
import ru.example.department.repositories.DepartmentRepository;
import ru.example.department.repositories.OfficeRepository;
import ru.example.department.util.NoEntryException;

import java.util.*;

@Slf4j
@Service("departmentService")
@Transactional
public class DepartmentServiceImpl implements DepartmentService {

    private DepartmentRepository departmentRepository;
    private OfficeRepository officeRepository;

    public DepartmentServiceImpl(DepartmentRepository departmentRepository, OfficeRepository officeRepository) {
        this.departmentRepository = departmentRepository;
        this.officeRepository = officeRepository;
    }

    @Override
    public DepartmentEntity findById(UUID id) throws NoEntryException {
        DepartmentEntity department = departmentRepository
                .findById(id)
                .orElseThrow(() -> new NoEntryException(id));
        department.writeOfficeIds();
        return department;
    }

    @Override
    public List<DepartmentEntity> findAllDepartments() {
        List<DepartmentEntity> departments = departmentRepository.findAll();
        for (DepartmentEntity department : departments) {
            department.writeOfficeIds();
        }
        return departments;
    }

    @Override
    public DepartmentEntity findByName(String name) {
        return departmentRepository.findByName(name);
    }

    @Override
    public void saveDepartment(DepartmentEntity department) {
        departmentRepository.save(department);
    }

    @Override
    public void updateDepartment(DepartmentEntity department, DepartmentEntity currentDepartment) {
        currentDepartment.setName(department.getName());
        for(UUID id : department.getOfficeIds()) {
            try {
                currentDepartment.addOffice(officeRepository
                        .findById(id)
                        .orElseThrow(() -> new NoEntryException(id)));
            } catch (NoEntryException e) {
                log.error(e.getErrorMessageEng());
            }
        }
        saveDepartment(currentDepartment);
    }

    @Override
    public void deleteDepartmentById(UUID id) throws NoEntryException {
        DepartmentEntity department = departmentRepository
                .findById(id)
                .orElseThrow(() -> new NoEntryException(id));
        department.remove();
        departmentRepository.delete(department);
    }

    @Override
    public void deleteAllDepartments() {
        departmentRepository.deleteAll();
    }

    @Override
    public boolean isDepartmentExist(DepartmentEntity department) {
        return findByName(department.getName()) != null;
    }
}
