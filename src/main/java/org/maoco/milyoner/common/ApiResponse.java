package org.maoco.milyoner.common;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@Builder
public class ApiResponse<T> {

    private Boolean success;
    private String message;
    private T data;
    private HttpStatus httpStatus;
    private Integer errorCode;

    public static <T> ApiResponse<T> success(T data) {
        return ApiResponse.<T>builder()
                .success(true)
                .message("Operation successful")
                .data(data)
                .httpStatus(HttpStatus.OK)
                .build();
    }

    public static <T> ApiResponse<T> failed(T data, HttpStatus httpStatus) {
        return ApiResponse.<T>builder()
                .success(false)
                .message("Operation was crashed")
                .data(data)
                .httpStatus(httpStatus)
                .build();
    }

    public static <T> ApiResponse<T> failed(String message, HttpStatus httpStatus) {
        return ApiResponse.<T>builder()
                .success(false)
                .message(message)
                .httpStatus(httpStatus)
                .build();
    }

    public static <T> ApiResponse<T> error(Integer errorCode, String message, HttpStatus httpStatus) {
        return ApiResponse.<T>builder()
                .success(false)
                .errorCode(errorCode)
                .message(message)
                .httpStatus(httpStatus)
                .build();
    }

}
