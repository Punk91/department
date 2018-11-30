package ru.example.department.model.core.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;


@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class Filter<T> {

    private String field;
    //private String type;
    private FilterTypeEnum type;
    private T value;
    //private String comparison;
    private FilterComparisonEnum comparison;

}