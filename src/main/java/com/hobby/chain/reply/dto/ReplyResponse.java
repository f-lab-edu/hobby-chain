package com.hobby.chain.reply.dto;

import lombok.Builder;

import java.sql.Date;

@Builder
public class ReplyResponse {
    private String nickName;
    private String content;
    private Date regdate;
}
