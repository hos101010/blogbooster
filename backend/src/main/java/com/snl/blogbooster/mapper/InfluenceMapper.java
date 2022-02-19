package com.snl.blogbooster.mapper;

import com.snl.blogbooster.model.dto.InfluenceRequestDto;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface InfluenceMapper {

    void insertInflKeyword(InfluenceRequestDto influenceRequestDto);
}
