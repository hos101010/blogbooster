package com.snl.blogbooster.model.domain.tag;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.snl.blogbooster.model.domain.posting.Posting;
import com.snl.blogbooster.model.domain.video.Video;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tagNum;

    @Column(insertable = false,updatable = false)
    private Long postingNum;

    @Column(insertable = false,updatable = false)
    private Long videoNum;
    private String tagType;
    private String content;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="postingNum")
    private Posting posting;


    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="videoNum")
    private Video video;

}
