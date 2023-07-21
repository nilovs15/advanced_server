package com.example.advanced_server.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class BaseSuccessResponse {
    private Integer statusCode;
    private boolean success;

    public static BaseSuccessResponse getResponse() {
        return new BaseSuccessResponse()
                .setStatusCode(1)
                .setSuccess(true);
    }
}
