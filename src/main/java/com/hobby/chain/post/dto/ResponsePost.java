package com.hobby.chain.post.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponsePost {
    private long postId;
    private long userIdx;
    private String postContent;
    private List<ImageDTO> images;
    private Timestamp regdate;
}
