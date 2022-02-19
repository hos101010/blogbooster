package com.snl.blogbooster.mapper;

import com.snl.blogbooster.model.domain.UserRankHistory;
import com.snl.blogbooster.model.dto.InfluenceRequestDto;
import com.snl.blogbooster.model.dto.UserRequestDto;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserMapper {

    void insertUserHistory(UserRankHistory userRankHistory);
    UserRankHistory getUserTodayRank(UserRequestDto userRequestDto);
}
