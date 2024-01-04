package com.hobby.chain.reply.service;

import com.hobby.chain.reply.domain.entity.Reply;
import com.hobby.chain.reply.domain.mapper.ReplyMapper;
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
        Reply reply = toEntity(postId, userId, content);
        replyMapper.insertReply(reply);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReplyResponse> getReplysByPostId(long postId, long startIdx) {
        return replyMapper.getReplysByPostId(postId, startIdx);
    }

    private Reply toEntity(long postId, long userId, String content){
        return Reply.builder()
                .postId(postId)
                .userId(userId)
                .content(content).build();
    }
}

