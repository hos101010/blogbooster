package com.snl.blogbooster.service;

import com.snl.blogbooster.common.CommonUtil;
import com.snl.blogbooster.mapper.InfluenceMapper;
import com.snl.blogbooster.mapper.UserMapper;
import com.snl.blogbooster.model.domain.RankerPosting;
import com.snl.blogbooster.model.domain.UserRankHistory;
import com.snl.blogbooster.model.dto.InfluenceRequestDto;
import com.snl.blogbooster.model.dto.UserRequestDto;
import com.snl.blogbooster.model.dto.UserResponseDto;
import com.snl.blogbooster.model.dto.WordRequestDto;
import io.swagger.models.auth.In;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;

    public UserRankHistory getUserRankInfo(UserRequestDto userRequestDto){

        String userId = userRequestDto.getUserId();
        UserRankHistory userRankHistory = userMapper.getUserTodayRank(userRequestDto);

        if( userRankHistory != null) //오늘 조회한 이력이있다면 해당 이력을 리턴
        {
            return userRankHistory;
        }
        else //없으면 측정시작
        {
            userRankHistory = CommonUtil.getUserScore(userId);
            userMapper.insertUserHistory(userRankHistory);
        }
        return userRankHistory;
    }

    public List<UserRankHistory> getKeywordRankerUsers(WordRequestDto wordRequestDto)
    {
        List<UserRankHistory> userRankHistoryList = new ArrayList<>();
        String keyword = wordRequestDto.getKeyword();
        List<RankerPosting> rankerPostings =CommonUtil.getKeywordViewRanker(keyword);
        for(RankerPosting rankerPosting : rankerPostings)
        {
            String userId = rankerPosting.getRegisterUserId();
            UserRequestDto userRequestDto = UserRequestDto.builder()
                    .userId(userId)
                    .build();
            UserRankHistory userRankHistory = userMapper.getUserTodayRank(userRequestDto);
            if(userRankHistory == null)
            {
                userRankHistory = CommonUtil.getUserScore(userId);
                userMapper.insertUserHistory(userRankHistory);
            }
            userRankHistoryList.add(userRankHistory);
        }
        return userRankHistoryList;
    }
}
