package com.example.advanced_server.dto.newsDto;


import com.example.advanced_server.exception.ValidationConstants;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
public class NewsDto {

    @NotBlank(message = ValidationConstants.NEWS_DESCRIPTION_NOT_NULL)
    @Length(min = 3, max = 160, message = ValidationConstants.NEWS_DESCRIPTION_SIZE_NOT_VALID)
    private String description;

    @NotNull(message = ValidationConstants.NEWS_IMAGE_HAS_TO_BE_PRESENT)
    @Length(min = 3, max = 130, message = ValidationConstants.NEWS_IMAGE_LENGTH)
    private String image;

    @NotNull(message = ValidationConstants.TAGS_NOT_VALID)
    private List<@NotBlank(message = ValidationConstants.TAGS_NOT_VALID) String> tags;

    @NotNull(message = ValidationConstants.NEWS_TITLE_NOT_NULL)
    @Length(min = 3, max = 160, message = ValidationConstants.NEWS_TITLE_SIZE)
    private String title;
}
