package com.snl.blogbooster.model.dto.posting;

import com.snl.blogbooster.model.domain.posting.Posting;
import com.snl.blogbooster.model.dto.tag.TagResponseDto;
import com.snl.blogbooster.model.dto.video.VideoResponseDto;
import lombok.*;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class PostingResponseDto {
    private Long postingNum;
    private String registerUserId;
    private String url;
    private String title;
    private Integer imageCount;
    private Integer videoCount;
    private Integer wordCount;

    private Set<TagResponseDto> tags;
    private Set<VideoResponseDto> videos;

}
