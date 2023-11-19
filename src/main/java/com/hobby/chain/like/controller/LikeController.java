package com.hobby.chain.like.controller;

import com.hobby.chain.like.service.LikeService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/likes")
public class LikeController {
    private final LikeService likeService;

    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @PostMapping
    public void like(Long userId, @RequestParam("postId") long postId) {
        likeService.like(userId, postId);
    }

    @DeleteMapping
    public void unlike(Long userId, @RequestParam("postId") long postId){
        likeService.unlike(userId, postId);
    }
}
