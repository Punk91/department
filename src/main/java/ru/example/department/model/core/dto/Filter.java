package ru.example.department.model.core.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;


@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class Filter<T> {

    private FilterTypeEnum type;
    private String field;
    private FilterComparisonEnum comparison;
    private List<String> values;

    public Filter(FilterTypeEnum type, String field, FilterComparisonEnum comparison, List<String> values) {
        this.type = type;
        this.field = field;
        this.comparison = comparison;
        this.values = values;
    }
}
