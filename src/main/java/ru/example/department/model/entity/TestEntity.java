package ru.example.department.model.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "Test")
public class TestEntity {
    @Id
    Long id;
    @Column
    String name;
}
