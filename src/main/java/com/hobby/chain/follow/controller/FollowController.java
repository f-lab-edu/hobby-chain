package com.hobby.chain.follow.controller;

import com.hobby.chain.follow.service.FollowService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/follows")
public class FollowController {
    private final FollowService followService;

    public FollowController(FollowService followService) {
        this.followService = followService;
    }

    @PostMapping
    public void subscribeUser(@RequestParam("userId") long follower, @RequestParam("followeeId") long followee){
        followService.subscribe(follower, followee);
    }

    @DeleteMapping
    public void unsubscribe(@RequestParam("userId") long follower, @RequestParam("followeeId") long followee){
        followService.unsubscribe(follower, followee);
    }
}
