package ru.example.department.model.core.dto;

import lombok.Data;
import java.util.List;

@Data
public class ReadRequest {
    private Integer page;
    private Integer size;
    private SortData sortData;
    private List<Filter> filterList;

    public ReadRequest(Integer page, Integer size, SortData sortData, List<Filter> filterList) {
        this.page = page;
        this.size = size;
        this.sortData = sortData;
        this.filterList = filterList;
    }


    @Override
    public String toString() {
        return "ReadRequest{" +
                "page=" + page +
                ", size=" + size +
                ", sortList=" + sortData +
                ", filterList=" + filterList +
                '}';
    }
}
