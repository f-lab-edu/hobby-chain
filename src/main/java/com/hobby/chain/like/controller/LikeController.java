package com.hobby.chain.like.controller;

import com.hobby.chain.like.service.LikeService;
import com.hobby.chain.member.dto.MemberId;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/likes")
public class LikeController {
    private final LikeService likeService;

    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @PostMapping
    public void like(MemberId memberId, @RequestParam("postId") long postId) {
        likeService.like(memberId.getUserId(), postId);
    }

    @DeleteMapping
    public void unlike(MemberId memberId, @RequestParam("postId") long postId){
        likeService.unlike(memberId.getUserId(), postId);
    }
}
