package com.hobby.chain.reply.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ReplyRequest {
    private long postId;
    private long userId;
    private String content;

    private ReplyRequest(){}
}
