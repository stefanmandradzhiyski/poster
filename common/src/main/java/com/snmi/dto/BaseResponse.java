package com.snmi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import static com.snmi.constants.GlobalConstants.EMPTY_STRING;

@Getter
@ToString
@NoArgsConstructor
@Schema(description = "The class is used as response wrapper")
public class BaseResponse<T> {

    private T data;
    private String message;
    private HttpStatus statusCode;

    private BaseResponse(final T data, final String message, final HttpStatus statusCode) {
        this.data = data;
        this.message = message;
        this.statusCode = statusCode;
    }

    public static <T> BaseResponse<T> build(final T data) {
        return new BaseResponse<>(data, EMPTY_STRING, HttpStatus.OK);
    }

    public static <T> BaseResponse<T> build(final String message) {
        return new BaseResponse<>(null, message, null);
    }

    public static <T> BaseResponse<T> build(final String message, final HttpStatus statusCode) {
        return new BaseResponse<>(null, message, statusCode);
    }

    public static <T> BaseResponse<T> build(final String message, final HttpStatusCode statusCode) {
        return new BaseResponse<>(null, message, (HttpStatus) statusCode);
    }

    public static <T> BaseResponse<T> build(final T data, final HttpStatus statusCode) {
        return new BaseResponse<>(data, EMPTY_STRING, statusCode);
    }

    public static <T> BaseResponse<T> build(final T data, final String message, final HttpStatus statusCode) {
        return new BaseResponse<>(data, message, statusCode);
    }

}
