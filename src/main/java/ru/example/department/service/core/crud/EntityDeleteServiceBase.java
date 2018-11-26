package ru.example.department.service.core.crud;

import ru.example.department.repository.core.BaseRepository;
import ru.example.department.service.core.converter.DtoEntityConverter;
import ru.example.department.service.core.log.LogChanges;

import javax.servlet.http.HttpSession;

public class EntityDeleteServiceBase<ENTITY, DTO, ID> implements EntityDeleteService<DTO, ID> {

    private BaseRepository<ENTITY, ID> repository;
    private DtoEntityConverter<DTO, ENTITY> dtoEntityConverter;
    private LogChanges logChanges;
    private String entityName;
    private static final String USERNAME_PARAM_NAME = "username";

    public EntityDeleteServiceBase(BaseRepository<ENTITY, ID> repository, DtoEntityConverter<DTO, ENTITY> dtoEntityConverter, LogChanges logChanges, String entityName) {
        this.repository = repository;
        this.dtoEntityConverter = dtoEntityConverter;
        this.logChanges = logChanges;
        this.entityName = entityName;
    }

    @Override
    public void delete(ID id, HttpSession session) {
        ENTITY deletedEntity = repository.getOne(id);
        DTO deletedDto= dtoEntityConverter.entityToDto(deletedEntity);
        repository.deleteById(id);
        logChanges.writeChangeLog(entityName, "Удаление", (String) session.getAttribute(USERNAME_PARAM_NAME), deletedDto,  null);
    }
}
