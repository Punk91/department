package ru.example.department.repository;

import org.springframework.stereotype.Repository;
import ru.example.department.model.entity.TestEntity;
import ru.example.department.repository.core.BaseRepository;

@Repository
public interface TestRepository extends BaseRepository<TestEntity, Long> {
}
