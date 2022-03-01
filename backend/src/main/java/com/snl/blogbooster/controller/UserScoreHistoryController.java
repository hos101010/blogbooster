package com.snl.blogbooster.controller;

import com.snl.blogbooster.model.dto.userScoreHistory.UserScoreRequestDto;
import com.snl.blogbooster.model.dto.userScoreHistory.UserScoreResponseDto;
import com.snl.blogbooster.service.UserScoreHistoryService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserScoreHistoryController {

    private final UserScoreHistoryService userScoreHistoryService;


    @GetMapping("/v1/data/user/score")
    public UserScoreResponseDto getUserScore(@RequestBody UserScoreRequestDto userScoreRequestDto)
    {
        return userScoreHistoryService.getUserScore(userScoreRequestDto.getUserId());
    }
}
