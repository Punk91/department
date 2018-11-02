package ru.example.department.service.converter;

import org.springframework.stereotype.Service;
import ru.example.department.model.dto.DepartmentDto;
import ru.example.department.model.entity.DepartmentEntity;
import ru.example.department.service.converter.base.DtoEntityConverter;

@Service
public class EnabledFeaturesDtoEntityConverter implements DtoEntityConverter<DepartmentDto, DepartmentEntity> {

    @Override
    public DepartmentEntity dtoToEntity(DepartmentDto dto) {
        DepartmentEntity entity = new DepartmentEntity();
        entity = updateEntityFromDto(dto, entity);
        return entity;
    }

    @Override
    public DepartmentEntity updateEntityFromDto(DepartmentDto dto, DepartmentEntity entity) {
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        return entity;
    }

    @Override
    public DepartmentDto entityToDto(DepartmentEntity entity) {
        DepartmentDto dto = new DepartmentDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        return dto;
    }
}
