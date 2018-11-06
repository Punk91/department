package ru.example.department.model.dto.base;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

/**
 * @author a.kotov
 * @since 22.03.2018
 */

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"code", "data", "error_msg", "rid"})
public class SwarmApiResponse<T> {
/*    private Integer code = Swarm2BaseResponseCode.OK.getId();
    private T data;
    @JsonProperty("error_msg")
    private String errorMsg;
    private String rid;

    public static SwarmApiResponse error(Integer code) {
        return error(code, SwarmApiResponseCode.getApiBaseResponse(code).getDescription());
    }

    public static SwarmApiResponse error(Integer code, String description) {
        SwarmApiResponse response = new SwarmApiResponse();
        response.setCode(code);
        response.setErrorMsg(description);
        return response;
    }

    public static SwarmApiResponse error (Swarm2BaseResponseCode code) {
        return error(code.getId(), code.getDescription());
    }

    public static SwarmApiResponse success() {
        return new SwarmApiResponse();
    }

    public static <T> SwarmApiResponse<T> success(T data) {
        SwarmApiResponse apiResponse = new SwarmApiResponse();
        apiResponse.setData(data);
        return apiResponse;
    }*/
}
