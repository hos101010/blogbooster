package com.snl.blogbooster.mapper;

import com.snl.blogbooster.model.domain.tag.Tag;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface TagMapper {

    void insertTag(Tag tag);
}
