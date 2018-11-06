package ru.example.department.repository;

import org.springframework.stereotype.Repository;
import ru.example.department.model.entity.TestEntity;

@Repository
public interface TestRepository extends BaseRepository<TestEntity, Long> {
}
