package ru.example.department.service.converter;

import org.springframework.stereotype.Service;
import ru.example.department.model.dto.TestDto;
import ru.example.department.model.entity.TestEntity;
import ru.example.department.service.converter.base.DtoEntityConverter;

@Service
public class TestDtoEntityConverter implements DtoEntityConverter<TestDto, TestEntity> {
    @Override
    public TestEntity dtoToEntity(TestDto dto) {
        TestEntity entity = new TestEntity();
        entity = updateEntityFromDto(dto, entity);
        return entity;
    }

    @Override
    public TestEntity updateEntityFromDto(TestDto dto, TestEntity entity) {
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        return entity;
    }

    @Override
    public TestDto entityToDto(TestEntity entity) {
        TestDto dto = new TestDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        return dto;
    }

}
