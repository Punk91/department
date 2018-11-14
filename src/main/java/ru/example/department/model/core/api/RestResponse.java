package ru.example.department.model.core.api;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class RestResponse<DTO> {

    private long totalCount;
    private boolean success;
    private List<DTO> data;
    private Integer errorCode;
    private String errorMessage;

    public RestResponse() {
    }

    public RestResponse(final DTO entity) {
        this.totalCount = 1;
        this.success = true;
        this.data = new ArrayList<DTO>() {{
            add(entity);
        }};
    }

    public RestResponse(boolean success, long totalCount, List<DTO> data) {
        this.totalCount = totalCount;
        this.success = success;
        this.data = data;
    }

}
