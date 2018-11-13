package ru.example.department.model.core;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class LogEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String login;

    @Column(name = "action_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date actionTime;

    @Column(name = "action_type")
    private String actionType;

    @Column(name = "entity_name")
    private String entityName;

    @Basic(fetch = FetchType.LAZY)
    @Lob
    @Column(name = "old_object",columnDefinition = "longblob")
    private byte[] oldObject;

    @Basic(fetch = FetchType.LAZY)
    @Lob
    @Column(name = "new_object",columnDefinition = "longblob")
    private byte[] newObject;

}
