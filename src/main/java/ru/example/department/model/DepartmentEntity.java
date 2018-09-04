package ru.example.department.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
@Table(name = "DEPARTMENT")
public class DepartmentEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "ID", updatable = false, nullable = false, columnDefinition = "BINARY(16)")
    private UUID id;

    @NotEmpty
    @Column(name = "NAME", nullable = false)
    private String name;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            },
            mappedBy = "departments")
    @JsonIgnore
    private Set<OfficeEntity> offices = new HashSet<>();

    @Transient
    private transient Set<UUID> officeIds = new HashSet<>();


    public DepartmentEntity() {
    }

    public void addOffice(OfficeEntity office) {
        offices.add(office);
        office.getDepartments().add(this);
    }

    public void removeOffice(OfficeEntity office) {
        offices.remove(office);
        office.getDepartments().remove(this);
    }

    public void remove() {
        for(OfficeEntity office : offices) {
            removeOffice(office);
        }
    }

    public void writeOfficeIds() {
        officeIds = offices.stream().map(OfficeEntity::getId).collect(Collectors.toSet());
    }

    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Set<OfficeEntity> getOffices() {
        return offices;
    }
    public void setOffices(Set<OfficeEntity> offices) {
        this.offices = offices;
    }
    public Set<UUID> getOfficeIds() {
        return officeIds;
    }
    public void setOfficeIds(Set<UUID> officeIds) {
        this.officeIds = officeIds;
    }
}
