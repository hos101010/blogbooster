package com.snl.blogbooster.controller;

import com.snl.blogbooster.model.dto.PostingRequestDto;
import com.snl.blogbooster.model.dto.keyword.KeywordRequestDto;
import com.snl.blogbooster.model.dto.keyword.KeywordResponseDto;
import com.snl.blogbooster.service.KeywordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class KeywordController {

    private final KeywordService keywordService;

    @GetMapping(value= "/v1/data/keyword", consumes = MediaType.APPLICATION_JSON_VALUE)
    public KeywordResponseDto getKeywordInfo(@RequestBody KeywordRequestDto keywordRequestDto)
    {
        return keywordService.getKeywordInfo(keywordRequestDto.getKeyword());
    }

}
