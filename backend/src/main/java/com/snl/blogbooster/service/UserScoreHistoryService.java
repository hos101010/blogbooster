package com.snl.blogbooster.service;

import com.snl.blogbooster.common.CommonUtil;
import com.snl.blogbooster.common.ModelMapperUtil;
import com.snl.blogbooster.mapper.UserMapper;
import com.snl.blogbooster.model.domain.RankerPosting;
import com.snl.blogbooster.model.domain.userScoreHistory.UserScoreHistory;
import com.snl.blogbooster.model.domain.userScoreHistory.UserScoreRepository;
import com.snl.blogbooster.model.dto.UserRequestDto;
import com.snl.blogbooster.model.dto.WordRequestDto;
import com.snl.blogbooster.model.dto.userScoreHistory.UserScoreResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserScoreHistoryService {

    private final UserMapper userMapper;

    private final UserScoreRepository userScoreRepository;

    public UserScoreResponseDto getUserScore(String userId){

        String today = CommonUtil.getyyyyMmddDate();

        UserScoreHistory userScoreHistory =userScoreRepository.findUserScoreHistoryByUserIdAndStandardDate(userId,today);

        if( userScoreHistory != null) //오늘 조회한 이력이있다면 해당 이력을 리턴
        {
            return ModelMapperUtil.getModelMapper().map(userScoreHistory,UserScoreResponseDto.class);
            //Entity to Dto
        }
        else //없으면 측정시작
        {
            //user Score 측정
            userScoreHistory = CommonUtil.getUserScore(userId);

            //user Score 저장후 Entity to Dto 리턴
            return ModelMapperUtil.getModelMapper().map(userScoreRepository.save(userScoreHistory),UserScoreResponseDto.class);
        }
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
