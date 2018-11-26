package ru.example.department.service;

import org.springframework.stereotype.Service;
import ru.example.department.model.dto.TestDto;
import ru.example.department.model.entity.TestEntity;
import ru.example.department.repository.core.BaseRepository;
import ru.example.department.service.converter.TestDtoEntityConverter;
import ru.example.department.service.core.converter.DtoEntityConverter;
import ru.example.department.service.core.crud.EntityCRUDServiceBase;
import ru.example.department.service.core.crud.EntityCreateService;
import ru.example.department.service.core.log.LogChanges;

import javax.annotation.PostConstruct;

@Service
public class TestService extends EntityCRUDServiceBase<TestEntity, TestDto, Long> {

    public TestService(BaseRepository<TestEntity, Long> repository, DtoEntityConverter<TestDto, TestEntity> dtoEntityConverter, LogChanges logChanges) {
        super(repository, dtoEntityConverter, logChanges, "Тестовая сущность");
    }

}
