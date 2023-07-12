package com.example.advanced_server.dto;

import lombok.Data;

@Data
public class BaseSuccessResponse {
    private Integer statusCode;
    private boolean success;

    public static BaseSuccessResponse getResponse() {
        BaseSuccessResponse response = new BaseSuccessResponse();
        response.setStatusCode(1);
        response.setSuccess(true);
        return response;
    }
}
