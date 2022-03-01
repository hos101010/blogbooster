package com.snl.blogbooster.model.dto.keyword;

import com.snl.blogbooster.model.domain.posting.Posting;
import lombok.*;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
public class KeywordRequestDto {

    private  String keyword;
}
