package com.snl.blogbooster.service;

import com.snl.blogbooster.common.CommonUtil;
import com.snl.blogbooster.mapper.UserMapper;
import com.snl.blogbooster.model.domain.RankerPosting;
import com.snl.blogbooster.model.domain.userScoreHistory.UserScoreHistory;
import com.snl.blogbooster.model.dto.UserRequestDto;
import com.snl.blogbooster.model.dto.WordRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;

    public UserScoreHistory getUserRankInfo(UserRequestDto userRequestDto){

        String userId = userRequestDto.getUserId();
        UserScoreHistory userRankHistory = userMapper.getUserTodayRank(userRequestDto);

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

    public List<UserScoreHistory> getKeywordRankerUsers(WordRequestDto wordRequestDto)
    {
        List<UserScoreHistory> userRankHistoryList = new ArrayList<>();
        String keyword = wordRequestDto.getKeyword();
        List<RankerPosting> rankerPostings =CommonUtil.getKeywordViewRanker(keyword);
        for(RankerPosting rankerPosting : rankerPostings)
        {
            String userId = rankerPosting.getRegisterUserId();
            UserRequestDto userRequestDto = UserRequestDto.builder()
                    .userId(userId)
                    .build();
            UserScoreHistory userRankHistory = userMapper.getUserTodayRank(userRequestDto);
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
