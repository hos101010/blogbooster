package com.snl.blogbooster.controller;

import com.snl.blogbooster.model.domain.RankerPosting;
import com.snl.blogbooster.model.dto.KeywordResponseDto;
import com.snl.blogbooster.model.dto.WordRequestDto;
import com.snl.blogbooster.service.WordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@Slf4j
@RestController
@RequiredArgsConstructor
public class WordController {

    private final WordService wordService;

    @PostMapping(value= "/v1/data/word", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void insertPosting(@RequestBody WordRequestDto wordRequestDto)
    {
        wordService.calculateWordCount(wordRequestDto);
    }

//    @GetMapping(value= "/v1/data/word/blog/ranker", consumes = MediaType.APPLICATION_JSON_VALUE)
//    public List<RankerPosting> getKeywordBlogRanker(@RequestBody WordRequestDto wordRequestDto)
//    {
//       return wordService.getKeywordBlogRanker(wordRequestDto.getKeyword());
//    }

//    @GetMapping(value= "/v1/data/word/view/ranker", consumes = MediaType.APPLICATION_JSON_VALUE)
//    public List<RankerPosting> getKeywordViewRanker(@RequestBody WordRequestDto wordRequestDto)
//    {
//        return wordService.getKeywordViewRanker(wordRequestDto.getKeyword());
//    }

//    @GetMapping(value="/v1/data/word", consumes = MediaType.APPLICATION_JSON_VALUE)
//    public KeywordResponseDto getWordInfo(@RequestBody WordRequestDto wordRequestDto)
//    {
//        return wordService.getWordInfo(wordRequestDto.getKeyword());
//    }


}
