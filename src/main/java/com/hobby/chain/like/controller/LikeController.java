package com.hobby.chain.like.controller;

import com.hobby.chain.like.service.LikeService;
import com.hobby.chain.member.dto.CertificatedMember;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/likes")
public class LikeController {
    private final LikeService likeService;

    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @PostMapping
    public void like(CertificatedMember memberId, @RequestParam("postId") long postId) {
        likeService.like(memberId.getUserId(), postId);
    }

    @DeleteMapping
    public void unlike(CertificatedMember memberId, @RequestParam("postId") long postId){
        likeService.unlike(memberId.getUserId(), postId);
    }
}
