package ru.example.department.service;

import org.springframework.stereotype.Service;
import ru.example.department.model.dto.TestDto;
import ru.example.department.model.entity.TestEntity;
import ru.example.department.repository.core.BaseRepository;
import ru.example.department.service.converter.TestDtoEntityConverter;
import ru.example.department.service.core.crud.EntityCRUDServiceBase;
import ru.example.department.service.core.crud.EntityCreateService;

import javax.annotation.PostConstruct;

@Service
public class TestService extends EntityCRUDServiceBase<TestEntity, TestDto, Long> {

/*    private EntityCreateService<TestEntity, TestDto> createService;
    private TestDtoEntityConverter testDtoEntityConverter;*/

    public TestService(BaseRepository<TestEntity, Long> repository, /*EntityCreateService<TestEntity, TestDto> createService,*/ TestDtoEntityConverter testDtoEntityConverter) {
        super(repository, testDtoEntityConverter, "Тестовая сущность");
/*        this.createService = createService;
        this.testDtoEntityConverter = testDtoEntityConverter;*/
    }

/*    @PostConstruct
    public void init(){
        super.setCreateService(createService);
    }*/

}
