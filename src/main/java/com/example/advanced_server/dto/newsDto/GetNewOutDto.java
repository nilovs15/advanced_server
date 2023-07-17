package com.example.advanced_server.dto.newsDto;

import java.util.List;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class GetNewOutDto {
    private String description;
    private Long id;
    private String image;
    private List<Tag> tags;
    private String title;
    private String userId;
    private String username;
}
