package ru.example.department.model.dto.base;

import java.util.List;

public class QueryResult<ENTITY_TYPE> {
    private final long totalCount;
    private final List<ENTITY_TYPE> list;

    public QueryResult(long totalCount, List<ENTITY_TYPE> list) {
        this.totalCount = totalCount;
        this.list = list;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public List<ENTITY_TYPE> getList() {
        return list;
    }
}
