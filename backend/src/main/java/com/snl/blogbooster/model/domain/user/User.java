package com.snl.blogbooster.model.domain.user;

import com.snl.blogbooster.model.domain.BaseEntity;
import com.snl.blogbooster.model.domain.userScoreHistory.UserScoreHistoryPK;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Builder
@AllArgsConstructor
@Getter
@NoArgsConstructor
@Entity
public class User extends BaseEntity {

    @Id
    private String userId;
    private String nickName;
    private String category;

}