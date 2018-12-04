package ru.example.department.model.core.dto;

import lombok.Data;
import java.util.List;

@Data
public class ReadRequest {
    private Integer page;
    private Integer size;
    private List<SortData> sortList;
    private List<Filter> filterList;

    public ReadRequest(Integer page, Integer size, List<SortData> sortList, List<Filter> filterList) {
        this.page = page;
        this.size = size;
        this.sortList = sortList;
        this.filterList = filterList;
    }


    @Override
    public String toString() {
        return "ReadRequest{" +
                "page=" + page +
                ", size=" + size +
                ", sortList=" + sortList +
                ", filterList=" + filterList +
                '}';
    }
}
