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
public class Posting extends BaseEntity{

    @Id
    private long postingNum;

    @Id
    private String registerUserId;
    private String url;
    private String title;
    private long imageCount;
    private long videoCount;
    private long wordCount;
}
