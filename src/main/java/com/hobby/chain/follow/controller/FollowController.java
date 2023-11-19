package com.hobby.chain.follow.controller;

import com.hobby.chain.follow.service.FollowService;
import com.hobby.chain.member.service.MemberLoginService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class FollowController {
    private final FollowService followService;

    public FollowController(FollowService followService) {
        this.followService = followService;
    }

    @PostMapping("/follows")
    public void subscribeUser(Long userId, @RequestParam("followeeId") long followee){
        followService.subscribe(userId, followee);
    }

    @DeleteMapping("/follows")
    public void unsubscribe(Long userId, @RequestParam("followeeId") long followee){
        followService.unsubscribe(userId, followee);
    }

    @GetMapping("/{userId}/followers")
    public List<Map<String, Long>> getFollowersInfo(@PathVariable("userId") long userId){
        return followService.getFollowerByUserId(userId);
    }

    @GetMapping("/{userId}/followees")
    public List<Map<String, Long>> getFolloweesInfo(@PathVariable("userId") long userId){
        return followService.getFolloweeByUserId(userId);
    }
}
