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
    public void like(@RequestParam("postId") long postId) {
        likeService.like(postId);
    }

    @DeleteMapping
    public void unlike(@RequestParam("postId") long postId){
        likeService.unlike(postId);
    }
}
