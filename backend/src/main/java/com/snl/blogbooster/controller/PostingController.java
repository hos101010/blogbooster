package com.snl.blogbooster.controller;

import com.snl.blogbooster.model.dto.PostingRequestDto;
import com.snl.blogbooster.service.PostingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.awt.*;

@Validated
@Slf4j
@RestController
@RequiredArgsConstructor
public class PostingController {

    private final PostingService postingService;

    @PostMapping(value= "/v1/data/posting", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void insertPosting(@RequestBody PostingRequestDto postingRequestDto)
    {
        String requestUserId ="ckrzkssja123";//다음에 Token값에서 가져오게 변경해야함
        postingService.insertPosting(postingRequestDto.getUrl(),requestUserId);
    }
}
