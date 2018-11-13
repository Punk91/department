package ru.example.department.repository.core;

import org.springframework.stereotype.Repository;
import ru.example.department.model.core.LogEntity;

@Repository
public interface LogRepo extends BaseRepository<LogEntity, Long> {
}
