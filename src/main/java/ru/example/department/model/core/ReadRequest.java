package ru.example.department.model.core;

import lombok.Data;
import java.util.List;

@Data
public class ReadRequest {

    private Integer page;
    private Integer size;
    private Integer start;
    private Integer limit;
    private List<SortData> sortList;
    private List<Filter> filterList;

    public ReadRequest(Integer page, Integer size, Integer start, Integer limit, List<SortData> sortList, List<Filter> filterList) {
        this.page = page;
        this.size = size;
        this.start = start;
        this.limit = limit;
        this.sortList = sortList;
        this.filterList = filterList;
    }


    @Override
    public String toString() {
        return "ReadRequest{" +
                "page=" + page +
                ", size=" + size +
                ", start=" + start +
                ", limit=" + limit +
                ", sortList=" + sortList +
                ", filterList=" + filterList +
                '}';
    }
}
