package ru.example.department.model.core.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;


@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class SortData {

    private String property;
    private String direction;

    public SortData(String property, String direction) {
        this.property = property;
        this.direction = direction;
    }

    @Override
    public String toString() {
        return "Sort{" +
                "property='" + property + '\'' +
                ", direction='" + direction + '\'' +
                '}';
    }
}
