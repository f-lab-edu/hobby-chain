package com.hobby.chain.reply.domain.entity;

import java.sql.Timestamp;

public class Reply {
    private Reply(){}

    private long postId;
    private long userId;
    private String content;
    private Timestamp regdate;
}
