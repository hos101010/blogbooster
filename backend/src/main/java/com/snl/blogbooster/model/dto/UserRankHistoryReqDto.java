package com.snl.blogbooster.model.dto;

import com.snl.blogbooster.model.domain.BaseEntity;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRankHistoryReqDto{

    private String userId;
    private String standardDate;
    private String category;
    private int totalPostingCount;
    private int monthPostingCount;
    private int dayVisitor;
    private int totalVisitor;
    private int neighborCount;
    private int tagCount;
    private double totalScore;

}
