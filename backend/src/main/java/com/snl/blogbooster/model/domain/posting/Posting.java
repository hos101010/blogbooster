package com.snl.blogbooster.model.domain.posting;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.snl.blogbooster.model.domain.BaseEntity;
import com.snl.blogbooster.model.domain.tag.Tag;
import com.snl.blogbooster.model.domain.video.Video;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Posting extends BaseEntity {

    @Id
    private Long    postingNum;
    private String  registerUserId;
    private String  url;
    private String  title;
    private String  postDate;
    private Integer imageCount;
    private Integer videoCount;
    private Integer wordCount;

    @JsonManagedReference
    @OneToMany(mappedBy = "posting",fetch = FetchType.EAGER)
    private Set<Tag> tags = new HashSet<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "posting", fetch = FetchType.EAGER)
    private Set<Video> videos = new HashSet<>();

}
