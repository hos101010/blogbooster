package com.snl.blogbooster.model.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;

import javax.persistence.Column;
import javax.validation.constraints.Min;

@Getter
@RequiredArgsConstructor
@Validated
public class PostingRequestDto {

    private String postingNum;

    @Column(length = 40)
    private String registerUserId;

    private String url;

    private String title;
}
