package ru.example.department.model.dto.base;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @since 17.09.2014
 */
@Data
@AllArgsConstructor
public class RestResponse<ENTITY_TYPE> {

    private long totalCount;
    private boolean success;
    private List<ENTITY_TYPE> data;
    private String errorMessage;

    public RestResponse() {
    }

    public RestResponse(final ENTITY_TYPE entity) {
        this.totalCount = 1;
        this.success = true;
        this.data = new ArrayList<ENTITY_TYPE>() {{
            add(entity);
        }};
    }

    public RestResponse(boolean success, long totalCount, List<ENTITY_TYPE> data) {
        this.totalCount = totalCount;
        this.success = success;
        this.data = data;
    }

}
