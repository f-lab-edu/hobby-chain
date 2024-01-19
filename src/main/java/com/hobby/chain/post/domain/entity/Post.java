package com.hobby.chain.post.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
@Builder
@AllArgsConstructor
public class Post {
    private long postId;
    private String userId;
    private String content;
    private Timestamp updatedate;
    private Timestamp regdate;

}


