package ru.example.department.service.base;

public interface EntityCRUDService<ENTITY, ID> extends EntityReadService<ENTITY, ID>, EntityCreateService<ENTITY>, EntityUpdateService<ENTITY>, EntityDeleteService<ID> {



}
