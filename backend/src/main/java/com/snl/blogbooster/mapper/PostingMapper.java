package com.snl.blogbooster.mapper;

import com.snl.blogbooster.model.domain.Posting;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface PostingMapper {

    int insertPosting(Posting posting);
    void insertRequestHistory(String url, String requestUserId);
}
