package com.snl.blogbooster.controller;

import com.snl.blogbooster.model.domain.posting.Posting;
import com.snl.blogbooster.model.dto.PostingRequestDto;
import com.snl.blogbooster.service.PostingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.awt.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PostingController {

    private final PostingService postingService;

    @GetMapping(value= "/v1/data/posting", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Posting getPostingInfo(@RequestBody PostingRequestDto postingRequestDto)
    {
//        postingService.save(postingRequestDto.getUrl());
        return postingService.findByUrl(postingRequestDto.getUrl());
    }

    @PostMapping(value= "/v1/data/posting", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Posting savePosting(@RequestBody PostingRequestDto postingRequestDto)
    {
        return postingService.save(postingRequestDto.getUrl());
    }
}
