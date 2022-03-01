package com.snl.blogbooster.domain.userScoreHistory;

import com.snl.blogbooster.model.domain.userScoreHistory.UserScoreRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserScoreHistoryTest {

    @Autowired
    UserScoreRepository userScoreRepository;

    @DisplayName("[사용자 점수 INSERT]")
    @Test
    public void 사용자점수_INSERT()
    {

    }
}
