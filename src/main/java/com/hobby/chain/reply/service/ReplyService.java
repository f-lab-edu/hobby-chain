package com.hobby.chain.reply.service;

import com.hobby.chain.reply.dto.ReplyResponse;

import java.util.List;

public interface ReplyService {
    public void writeReply(long postId, long userId, String content);
    public List<ReplyResponse> getReplysByPostId(long postId, long startIdx);
}
