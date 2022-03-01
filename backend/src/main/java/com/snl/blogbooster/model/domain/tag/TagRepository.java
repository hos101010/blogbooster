package com.snl.blogbooster.model.domain.tag;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag,Long> {

    @Transactional
    void deleteAlByPostingNum(long postingNum);

}
