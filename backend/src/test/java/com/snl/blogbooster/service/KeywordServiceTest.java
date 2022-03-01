package com.snl.blogbooster.service;

import com.snl.blogbooster.common.CommonUtil;
import com.snl.blogbooster.model.dto.keyword.KeywordResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class KeywordServiceTest {

    @Autowired
    PostingService postingService;

    @Autowired
    KeywordService keywordService;

    @Test
    public void 키워드_결과_GET()
    {
        String keyword ="쿼스트 프로틴칩";
//        keywordService.getKeywordInfo(keyword);
        CommonUtil.getKeywordViewRanker(keyword);
    }
}
