package ru.example.department.service.core.crud;

public interface EntityCRUDService<ENTITY, ID, DTO> extends EntityReadService<ENTITY, ID>, EntityCreateService<ENTITY, DTO>, EntityUpdateService<ENTITY>, EntityDeleteService<ID> {

}
