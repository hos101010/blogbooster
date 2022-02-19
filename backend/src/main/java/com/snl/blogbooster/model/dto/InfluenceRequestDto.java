package com.snl.blogbooster.model.dto;

import lombok.*;
import org.springframework.validation.annotation.Validated;

@Setter
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Validated
public class InfluenceRequestDto {

    private String keyword;
    private String publishDate;
    private String ranking;

}
