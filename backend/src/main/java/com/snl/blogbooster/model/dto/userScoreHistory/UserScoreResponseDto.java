package com.snl.blogbooster.model.dto.userScoreHistory;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
public class UserScoreResponseDto {

    private String userId;
    private String nickName;
    private String category;
    private String standardDate;
    private Long totalPostingCount;
    private Long monthPostingCount;
    private Long dayVisitor;
    private Long totalVisitor;
    private Long neighborCount;
    private Long tagCount;
    private Double totalScore;
}
