package ru.example.department.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.example.department.model.entity.TestEntity;
import ru.example.department.repository.BaseRepository;
import ru.example.department.service.base.EntityCRUDServiceBase;
import ru.example.department.service.base.EntityCreateService;

import javax.annotation.PostConstruct;

@Service
public class TestService extends EntityCRUDServiceBase<TestEntity, Long> {

    private EntityCreateService<TestEntity> createService;

    public TestService(BaseRepository<TestEntity, Long> repository, EntityCreateService<TestEntity> createService) {
        super(repository);
        this.createService = createService;
    }

    @PostConstruct
    public void init(){
        super.setCreateService(createService);
    }

}
