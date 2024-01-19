package com.hobby.chain.follow.controller;

import com.hobby.chain.follow.service.FollowService;
import com.hobby.chain.member.dto.CertificatedMember;
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
    public void subscribeUser(CertificatedMember memberId, @RequestParam("followeeId") long followee){
        followService.subscribe(memberId.getUserId(), followee);
    }

    @DeleteMapping("/follows")
    public void unsubscribe(CertificatedMember memberId, @RequestParam("followeeId") long followee){
        followService.unsubscribe(memberId.getUserId(), followee);
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
