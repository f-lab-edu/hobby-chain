package com.hobby.chain.reply.domain.entity;

import lombok.Builder;

import java.sql.Timestamp;

@Builder
public class Reply {
    private Reply(){}

    private long postId;
    private long userId;
    private String content;
    private Timestamp regdate;
}
