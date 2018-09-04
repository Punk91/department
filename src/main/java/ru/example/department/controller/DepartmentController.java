package ru.example.department.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.example.department.model.DepartmentEntity;
import ru.example.department.service.DepartmentService;
import ru.example.department.util.CustomErrorType;
import ru.example.department.util.DConst;
import ru.example.department.util.NoEntryException;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(DConst.API_NAME + DConst.CONTROLLER_DEPARTMENT)
public class DepartmentController {

    private DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping
    public ResponseEntity<?> listAllDepartments() {
        List<DepartmentEntity> departments = departmentService.findAllDepartments();
        return new ResponseEntity<List<DepartmentEntity>>(departments, HttpStatus.OK);
    }

    @GetMapping(value = "{id}/")
    public ResponseEntity<?> getDepartments(@PathVariable("id") UUID id) {
        DepartmentEntity department = null;
        try {
            department = departmentService.findById(id);
        } catch (NoEntryException e) {
            return new ResponseEntity<>(new CustomErrorType(e.getErrorMessageRus()), e.getStatus());
        }
        return new ResponseEntity<DepartmentEntity>(department, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createDepartment(@RequestBody DepartmentEntity department) {
        departmentService.saveDepartment(department);
        return new ResponseEntity<DepartmentEntity>(department, HttpStatus.CREATED);
    }

    @PutMapping(value = "{id}/")
    public ResponseEntity<?> updateDepartment(@PathVariable("id") UUID id, @RequestBody DepartmentEntity department) {
        DepartmentEntity currentDepartment = null;
        try {
            currentDepartment = departmentService.findById(id);
        } catch (NoEntryException e) {
            return new ResponseEntity<>(new CustomErrorType(e.getErrorMessageRus()), e.getStatus());
        }
        departmentService.updateDepartment(department, currentDepartment);
        return new ResponseEntity<DepartmentEntity>(currentDepartment, HttpStatus.OK);
    }

    @DeleteMapping(value = "{id}/")
    public ResponseEntity<?> deleteDepartment(@PathVariable("id") UUID id) {
        try {
            departmentService.deleteDepartmentById(id);
        } catch (NoEntryException e) {
            return new ResponseEntity<>(new CustomErrorType(e.getErrorMessageRus()), e.getStatus());
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

/*    @DeleteMapping
    public ResponseEntity<?> deleteAllDepartments() {
        departmentService.deleteAllDepartments();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }*/
}
