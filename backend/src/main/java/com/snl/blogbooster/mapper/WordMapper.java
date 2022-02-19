package com.snl.blogbooster.mapper;

import com.snl.blogbooster.model.domain.Tag;
import com.snl.blogbooster.model.domain.Word;
import com.snl.blogbooster.model.dto.InfluenceRequestDto;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface WordMapper {

    void insertWord(Word word);

    void deleteWordHistory(String url);

}
