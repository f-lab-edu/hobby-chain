package com.hobby.chain.follow.controller;

import com.hobby.chain.follow.service.FollowService;
import com.hobby.chain.member.service.MemberLoginService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/follows")
public class FollowController {
    private final FollowService followService;

    public FollowController(FollowService followService) {
        this.followService = followService;
    }

    @PostMapping
    public void subscribeUser(@RequestParam("followeeId") long followee){
        followService.subscribe(followee);
    }

    @DeleteMapping
    public void unsubscribe(@RequestParam("followeeId") long followee){
        followService.unsubscribe(followee);
    }
}
