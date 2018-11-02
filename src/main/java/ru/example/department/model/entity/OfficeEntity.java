package ru.example.department.model.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "OFFICE")
public class OfficeEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "ID", updatable = false, nullable = false, columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(name = "CITY", nullable = false)
    private String city;

    @Column(name = "ADDRESS", nullable = false)
    private String address;

    @Enumerated(EnumType.STRING)
    @Column(name = "CATEGORY", nullable = false)
    private CategoryEnum category;

    @Enumerated(EnumType.STRING)
    @Column(name = "PROPERTY_TYPE", nullable = false)
    private PropertyTypeEnum propertyType;

    @Column(name = "VALUE", nullable = false)
    private Double value;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(name = "OFFICE_DEPARTMENT",
            joinColumns = { @JoinColumn(name = "OFFICE_ID ") },
            inverseJoinColumns = { @JoinColumn(name = "DEPARTMENT_ID") })

    private Set<DepartmentEntity> departments = new HashSet<>();

    public OfficeEntity() {
    }


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public CategoryEnum getCategory() {
        return category;
    }

    public void setCategory(CategoryEnum category) {
        this.category = category;
    }

    public PropertyTypeEnum getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(PropertyTypeEnum propertyType) {
        this.propertyType = propertyType;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Set<DepartmentEntity> getDepartments() {
        return departments;
    }

    public void setDepartments(Set<DepartmentEntity> departments) {
        this.departments = departments;
    }
}


