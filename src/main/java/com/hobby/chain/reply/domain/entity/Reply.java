package com.hobby.chain.reply.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Builder
@AllArgsConstructor
public class Reply {
    private long postId;
    private long userId;
    private String content;
    private Timestamp regdate;
}
