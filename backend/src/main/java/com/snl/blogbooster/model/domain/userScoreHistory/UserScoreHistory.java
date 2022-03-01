package com.snl.blogbooster.model.domain.userScoreHistory;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@IdClass(UserScoreHistoryPK.class)
@Builder
@Entity
public class UserScoreHistory {

    @Id
    private String userId;

    @Id
    private String standardDate;
    private Long totalPostingCount;
    private Long monthPostingCount;
    private Long dayVisitor;
    private Long totalVisitor;
    private Long neighborCount;
    private Long tagCount;
    private Double totalScore;

}
