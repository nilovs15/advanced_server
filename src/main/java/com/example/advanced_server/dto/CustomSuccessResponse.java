package com.example.advanced_server.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomSuccessResponse<T> {
    private T data;
    private int statusCode;
    private boolean success = true;

    private List<Integer> codes;
    private Integer code;

    public static <T> CustomSuccessResponse<T> getResponse(T data) {
        return new CustomSuccessResponse<T>().setStatusCode(1).setData(data);
    }
    public static <T> CustomSuccessResponse<T> getResponse() {
        return new CustomSuccessResponse<T>().setStatusCode(1);
    }

    public static <T> CustomSuccessResponse<T> getBadResponse(List<Integer> codes, Integer code) {
        return new CustomSuccessResponse<T>().setCodes(codes).setCode(codes.get(0)).setStatusCode(code);
    }
    public static <T> CustomSuccessResponse<T> getBadResponse(Integer code) {
        return new CustomSuccessResponse<T>().setStatusCode(code);
    }
}
