package com.snl.blogbooster.model.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SearchResult {
    private  int mobileSearchCount;
    private  int pcSearchCount;
    private  int totalSearchCount;
    private  String complexIndex;
}
