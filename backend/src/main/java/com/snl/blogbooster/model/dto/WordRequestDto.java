package com.snl.blogbooster.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;

@Getter
@RequiredArgsConstructor
@Validated
public class WordRequestDto {

    private String url;

    private String keyword;

}
