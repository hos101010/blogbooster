package com.snl.blogbooster.service;

import com.snl.blogbooster.mapper.TagMapper;
import com.snl.blogbooster.mapper.VideoMapper;
import com.snl.blogbooster.model.domain.Tag;
import com.snl.blogbooster.model.domain.Video;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TagService {

    private final TagMapper tagMapper;

    public void insertTag(Tag tag)
    {
        tagMapper.insertTag(tag);
    }
}
