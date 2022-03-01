package com.snl.blogbooster.model.domain.posting;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PostingRepository extends JpaRepository<Posting,Long> {

    Posting findPostingByPostingNum(long postingNum);

    Posting save(Posting posting);

}
