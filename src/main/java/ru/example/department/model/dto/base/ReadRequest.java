package ru.example.department.model.dto.base;

import java.util.List;

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

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public List<SortData> getSortList() {
        return sortList;
    }

    public void setSortList(List<SortData> sortList) {
        this.sortList = sortList;
    }

    public List<Filter> getFilterList() {
        return filterList;
    }

    public void setFilterList(List<Filter> filterList) {
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
