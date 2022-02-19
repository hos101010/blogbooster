package com.snl.blogbooster.model.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RankerPosting {

    private int ranking;
    private boolean isInfluence;
    private String registerUserId;
    private String registerUserName;
    private String title;
    private String url;
    private String description;
    private String postdate;
    private long postingNum;
    private List<String> tags;

    public boolean isInfluence(){return isInfluence;}

}
