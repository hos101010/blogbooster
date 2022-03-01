package com.snl.blogbooster.domain.posting;

import com.snl.blogbooster.model.domain.posting.Posting;
import com.snl.blogbooster.model.domain.posting.PostingRepository;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PostingRepositoryTest {

    @Autowired
    PostingRepository postingRepository;

    @DisplayName("[포스팅 INSERT]")
    @Test
    public void 포스팅_INSERT(){

        Posting posting = Posting.builder()
                .postingNum(13L)
                .imageCount(0)
                .registerUserId("ckrzkssja123")
                .title("test")
                .url("test")
                .videoCount(0)
                .wordCount(0)
                .build();

        postingRepository.save(posting);
    }

}
