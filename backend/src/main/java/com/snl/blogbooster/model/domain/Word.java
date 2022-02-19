package com.snl.blogbooster.model.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Word {

    @Id
    @GeneratedValue
    private long wordNum;
    private long repeatCount;
    private String registerUserId;
    private String url;
    private String value;
    private String wordType;

}
