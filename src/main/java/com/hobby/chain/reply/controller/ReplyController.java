package com.hobby.chain.reply.controller;

import com.hobby.chain.reply.dto.ReplyResponse;
import com.hobby.chain.reply.service.ReplyService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ReplyController {
    private final ReplyService replyService;

    public ReplyController(ReplyService replyService) {
        this.replyService = replyService;
    }

    @GetMapping("/{postId}")
    public List<ReplyResponse> getReplysByPostId(@PathVariable long postId, @RequestParam(defaultValue = "0") long startIdx){
        return replyService.getReplysByPostId(postId, startIdx);
    }

    @PostMapping("/{postId}")
    public void writeReply(@PathVariable long postId, Long userId, String content){
        replyService.writeReply(postId, userId, content);
    }

}
