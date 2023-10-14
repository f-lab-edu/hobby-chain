package com.hobby.chain.follow.service;

public interface FollowService {
    public void subscribe(long followee);
    public void unsubscribe(long followee);
    public boolean isFollowing(long follower, long followee);
}
