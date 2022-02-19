package com.snl.blogbooster.controller;

import com.snl.blogbooster.model.domain.UserRankHistory;
import com.snl.blogbooster.model.dto.UserRequestDto;
import com.snl.blogbooster.model.dto.WordRequestDto;
import com.snl.blogbooster.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Validated
@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping(value= "/v1/data/userRankInfo", consumes = MediaType.APPLICATION_JSON_VALUE)
    public UserRankHistory userRankInfo(@RequestBody UserRequestDto userRequestDto)
    {
       return userService.getUserRankInfo(userRequestDto);
    }

    @GetMapping(value= "/v1/data/keyword/userRankInfo", consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<UserRankHistory> getKeywordRankerUsers(@RequestBody WordRequestDto wordRequestDto)
    {
        return userService.getKeywordRankerUsers(wordRequestDto);
    }

}
