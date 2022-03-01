package com.snl.blogbooster.model.domain.video;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.snl.blogbooster.model.domain.posting.Posting;
import com.snl.blogbooster.model.domain.tag.Tag;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Video {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long videoNum;

    @Column(updatable = false,insertable = false)
    private Long postingNum;
    private String title;
    private String description;

    @JsonManagedReference
    @OneToMany(mappedBy = "video", fetch = FetchType.EAGER)
    private Set<Tag> tags = new HashSet<>();

    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="postingNum")
    private Posting posting;
}
