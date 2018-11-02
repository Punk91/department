package ru.example.department.service.converter.base;

import java.util.LinkedList;
import java.util.List;

public interface DtoEntityConverter<DTO_TYPE,ENTITY_TYPE>  {

    ENTITY_TYPE dtoToEntity(DTO_TYPE dto);

    ENTITY_TYPE updateEntityFromDto(DTO_TYPE dto, ENTITY_TYPE entity);

    DTO_TYPE entityToDto(ENTITY_TYPE entity);

    default List<DTO_TYPE> entityListToDtoList(List<ENTITY_TYPE> entityList) {

        List<DTO_TYPE> listDto = new LinkedList<>();

        for(ENTITY_TYPE entity : entityList){
            listDto.add(entityToDto(entity));
        }
        return listDto;
    }
}
