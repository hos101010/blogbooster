package com.snl.blogbooster.model.dto.keyword;

import com.snl.blogbooster.model.domain.posting.Posting;
import com.snl.blogbooster.model.dto.posting.PostingResponseDto;
import com.snl.blogbooster.model.dto.tag.TagResponseDto;
import lombok.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
public class KeywordResponseDto {

    private  String keyword;
    private  Integer mobileSearchCount;
    private  Integer pcSearchCount;
    private  Integer totalSearchCount;
    private  String complexIndex;
    private Integer dayPublishCount;
    private Integer weekPublishCount;
    private Integer monthPublishCount;
    private Integer totalPublishCount;
    private Set<PostingResponseDto> postings;
}
