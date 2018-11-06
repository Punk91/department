package ru.example.department.model.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class TestEntity {
    @Id
    Long id;
    @Column
    String name;
}
