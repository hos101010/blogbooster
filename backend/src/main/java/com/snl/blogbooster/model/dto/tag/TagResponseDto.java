package com.snl.blogbooster.model.dto.tag;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TagResponseDto {

    private Long postingNum;
    private Long videoNum;
    private String tagType;
    private String content;
}
