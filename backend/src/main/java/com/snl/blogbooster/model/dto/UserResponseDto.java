package com.snl.blogbooster.model.dto;

import lombok.*;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Map;

@Setter
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Validated
public class UserResponseDto {

    private String userId;
    private String category;
    private int totalPostingCount;
    private int postingCountInAMonth;
    private int dayVisitor;
    private int totalVisitor;
    private int neighborCount;
    private int tagCount;
    private double totalScore;

}
