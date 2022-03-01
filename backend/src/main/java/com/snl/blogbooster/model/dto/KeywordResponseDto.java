package com.snl.blogbooster.model.dto;

import com.snl.blogbooster.model.domain.KeywordSearchResult;
import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KeywordResponseDto {

    private String keyword;
    private int dayPublishCount;
    private int weekPublishCount;
    private int monthPublishCount;
    private int totalPublishCount;
    private KeywordSearchResult searchResult;
}
