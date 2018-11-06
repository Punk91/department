package ru.example.department.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.example.department.model.dto.TestDto;
import ru.example.department.model.dto.base.RestResponse;
import ru.example.department.model.entity.TestEntity;
import ru.example.department.service.TestService;
import ru.example.department.service.converter.TestDtoEntityConverter;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/test/")
public class TestController {

    @Autowired
    private TestService service;
    @Autowired
    private TestDtoEntityConverter converter;


    @PostMapping
    public RestResponse<TestDto> create(@RequestBody TestDto dto, HttpSession session) {
        TestEntity entity = converter.dtoToEntity(dto);
        TestEntity createdEntity = service.create(entity, session);
        TestDto createdDto = converter.entityToDto(createdEntity);
        return new RestResponse<>(createdDto);
    }

/*    @GetMapping
    public*/

}
