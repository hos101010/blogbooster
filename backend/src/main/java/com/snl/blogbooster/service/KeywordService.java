package com.snl.blogbooster.service;

import com.snl.blogbooster.common.CommonUtil;
import com.snl.blogbooster.common.ModelMapperUtil;
import com.snl.blogbooster.model.domain.posting.Posting;
import com.snl.blogbooster.model.dto.keyword.KeywordResponseDto;
import com.snl.blogbooster.model.dto.posting.PostingResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class KeywordService {

    private final PostingService postingService;

    public KeywordResponseDto getKeywordInfo(String keyword)
    {
        //view탭 5등까지의 url가져오기
        List<String> rankerUrls = CommonUtil.getKeywordViewRankers(keyword);
        Set<PostingResponseDto> postings = new HashSet<>();
        for(String url : rankerUrls)
        {
            Posting posting = postingService.save(url);
            PostingResponseDto postingResponseDto = ModelMapperUtil.getModelMapper().map(posting,PostingResponseDto.class);
            postings.add(postingResponseDto);
        }

        KeywordResponseDto keywordResponseDto = CommonUtil.getKeyWordInfo(keyword);
        keywordResponseDto.setKeyword(keyword);
        keywordResponseDto.setPostings(postings);
        return keywordResponseDto;
    }
}
