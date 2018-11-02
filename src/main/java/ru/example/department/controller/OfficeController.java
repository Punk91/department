package ru.example.department.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.example.department.model.entity.OfficeEntity;
import ru.example.department.service.DepartmentService;
import ru.example.department.service.OfficeService;
import ru.example.department.util.CustomErrorType;
import ru.example.department.util.DConst;
import ru.example.department.util.NoEntryException;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(DConst.API_NAME + DConst.CONTROLLER_OFFICE)
public class OfficeController {

    private OfficeService officeService;
    @Autowired
    private DepartmentService departmentService;

    public OfficeController(OfficeService officeService) {
        this.officeService = officeService;
    }

    @GetMapping
    public ResponseEntity<?> listAllOffices() {
        List<OfficeEntity> departments = officeService.findAllOffices();
        return new ResponseEntity<List<OfficeEntity>>(departments, HttpStatus.OK);
    }

    @GetMapping(value = "{id}/")
    public ResponseEntity<?> getOffices(@PathVariable("id") UUID id) {
        OfficeEntity office = null;
        try {
            office = officeService.findById(id);
        } catch (NoEntryException e) {
            return new ResponseEntity<>(new CustomErrorType(e.getErrorMessageRus()), e.getStatus());
        }
        return new ResponseEntity<OfficeEntity>(office, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createOffice(@RequestBody OfficeEntity office) {

/*
        OfficeEntity office1 = new OfficeEntity();
        office1.setAddress("address test");
        office1.setValue(25686D);
        office1.setPropertyType(PropertyTypeEnum.PRIVATE);
        office1.setCity("City-Test");
        office1.setCategory(CategoryEnum.C);

        DepartmentEntity d2 = departmentService.findByName("testQ2");
        DepartmentEntity d3 = departmentService.findByName("testQ3");

        office1.getDepartments().add(d2);
        office1.getDepartments().add(d3);

        d2.getOffices().add(office1);
        d3.getOffices().add(office1);
*/


        officeService.saveOffice(office);
        return new ResponseEntity<OfficeEntity>(office, HttpStatus.CREATED);
    }

    @PutMapping(value = "{id}/")
    public ResponseEntity<?> updateOffice(@PathVariable("id") UUID id, @RequestBody OfficeEntity office) {
        OfficeEntity currentOffice = null;
        try {
            currentOffice = officeService.findById(id);
        } catch (NoEntryException e) {
            return new ResponseEntity<>(new CustomErrorType(e.getErrorMessageRus()), e.getStatus());
        }
        officeService.updateOffice(office, currentOffice);
        return new ResponseEntity<OfficeEntity>(currentOffice, HttpStatus.OK);
    }

    @DeleteMapping(value = "{id}/")
    public ResponseEntity<?> deleteOffice(@PathVariable("id") UUID id) {
        try {
            officeService.deleteOfficeById(id);
        } catch (NoEntryException e) {
            return new ResponseEntity<>(new CustomErrorType(e.getErrorMessageRus()), e.getStatus());
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

/*    @DeleteMapping
    public ResponseEntity<?> deleteAllOffices() {
        officeService.deleteAllOffice();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }*/
}
