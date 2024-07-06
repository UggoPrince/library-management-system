package com.libraryservice.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.Map;

@ToString
@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class ApiResponse<T> {
    private String message = "Successful";
    private Boolean success = false;
    private T data;
    private Map<String, String> error;

    public ApiResponse() {}
    public ApiResponse(String message, Boolean success, T data, Map<String, String> error) {
        setMessage(message);
        setData(data);
        setSuccess(success);
        setError(error);
    }

    public ApiResponse<T> successResponse(T data, String message) {
        setData(data);
        setSuccess(true);
        setMessage(message);
        return this;
        // return new ApiResponse<>(message, true, data, null);
    }
}
