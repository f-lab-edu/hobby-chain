package com.hobby.chain.reply.domain.mapper;

import com.hobby.chain.reply.dto.ReplyRequest;
import com.hobby.chain.reply.dto.ReplyResponse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ReplyMapper {
    public void insertReply(ReplyRequest replyRequest);
    public List<ReplyResponse> getReplysByPostId(@Param("postId") long postId, @Param("startIdx") long startIdx);
}
