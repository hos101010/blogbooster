package com.snl.blogbooster.model.domain.userScoreHistory;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserScoreRepository extends JpaRepository<UserScoreHistory,String> {

    UserScoreHistory findUserScoreHistoryByUserIdAndStandardDate(String userId, String standardDate);
    UserScoreHistory save(UserScoreHistory userScoreHistory);

}
