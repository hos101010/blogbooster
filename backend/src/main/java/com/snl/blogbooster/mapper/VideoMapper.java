package com.snl.blogbooster.mapper;

import com.snl.blogbooster.model.domain.Video;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface VideoMapper {

    int insertVideo(Video video);
    void deleteVideo(long postingNum);
}
