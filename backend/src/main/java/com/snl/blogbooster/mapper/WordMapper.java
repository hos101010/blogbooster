package com.snl.blogbooster.mapper;

import com.snl.blogbooster.model.domain.Word;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface WordMapper {

    void insertWord(Word word);

    void deleteWordHistory(String url);

}
