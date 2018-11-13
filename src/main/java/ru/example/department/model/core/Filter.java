package ru.example.department.model.core;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;


@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class Filter<T> {

    private String field;
    private String type;
    private T value;
    private String comparison;

}
