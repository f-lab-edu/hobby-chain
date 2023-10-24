package com.hobby.chain.reply.service;

import com.hobby.chain.reply.domain.mapper.ReplyMapper;
import com.hobby.chain.reply.dto.ReplyRequest;
import com.hobby.chain.reply.dto.ReplyResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReplyServiceImpl implements ReplyService{
    private final ReplyMapper replyMapper;

    public ReplyServiceImpl(ReplyMapper replyMapper) {
        this.replyMapper = replyMapper;
    }

    @Override
    @Transactional
    public void writeReply(long postId, long userId, String content) {
        ReplyRequest request = toRequest(postId, userId, content);
        replyMapper.insertReply(request);
    }

    @Override
    public List<ReplyResponse> getReplysByPostId(long postId) {
        return replyMapper.getReplysByPostId(postId);
    }

    private ReplyRequest toRequest(long postId, long userId, String content){
        return ReplyRequest.builder()
                .postId(postId)
                .userId(userId)
                .content(content).build();
    }
}

