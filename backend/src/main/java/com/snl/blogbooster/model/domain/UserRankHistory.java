package com.snl.blogbooster.model.domain;

import lombok.*;
import org.springframework.validation.annotation.Validated;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRankHistory{

    @Id
    private String userId;

    @Id
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
