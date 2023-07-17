package com.example.advanced_server.dto.newsDto;

import java.util.List;

import lombok.Data;
import lombok.experimental.Accessors;


@Data
@Accessors(chain = true)
public class PageableResponse {
    private List<GetNewOutDto> content;
    private Long numberOfElements;
    public static PageableResponse getResponse(List<GetNewOutDto> content) {
        return new PageableResponse()
                .setContent(content)
                .setNumberOfElements((long) content.size());
    }
}
