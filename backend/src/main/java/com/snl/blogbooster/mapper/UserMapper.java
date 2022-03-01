package com.snl.blogbooster.mapper;

import com.snl.blogbooster.model.domain.userScoreHistory.UserScoreHistory;
import com.snl.blogbooster.model.dto.UserRequestDto;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserMapper {

    void insertUserHistory(UserScoreHistory userRankHistory);
    UserScoreHistory getUserTodayRank(UserRequestDto userRequestDto);
}
