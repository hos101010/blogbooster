package com.snl.blogbooster.model.dto.video;

import com.snl.blogbooster.model.dto.tag.TagResponseDto;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
public class VideoResponseDto {

    private String title;
    private String description;
    private Set<TagResponseDto> tags;

}
