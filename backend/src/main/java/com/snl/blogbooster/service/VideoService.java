package com.snl.blogbooster.service;

import com.snl.blogbooster.mapper.VideoMapper;
import com.snl.blogbooster.model.domain.Video;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class VideoService {

    private final VideoMapper videoMapper;

    public int insertVideo(Video video)
    {
        return videoMapper.insertVideo(video);
    }

    public void deleteVideo(long postingNum)
    {
        videoMapper.deleteVideo(postingNum);
    }

}
