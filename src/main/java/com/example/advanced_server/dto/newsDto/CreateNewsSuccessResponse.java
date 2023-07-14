package com.example.advanced_server.dto.newsDto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CreateNewsSuccessResponse {
    private Long id;
    private Integer statusCode;
    private boolean success;

    public static CreateNewsSuccessResponse getResponse(Long id) {
        return new CreateNewsSuccessResponse()
                .setId(id)
                .setStatusCode(1)
                .setSuccess(true);
    }
}
