package com.snl.blogbooster.mapper;

import com.snl.blogbooster.model.domain.Tag;
import com.snl.blogbooster.model.domain.Video;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface TagMapper {

    void insertTag(Tag tag);
}
