package com.hobby.chain.follow.service;

public interface FollowService {
    public void subscribe(long follower, long followee);
    public void unsubscribe(long follower, long followee);
    public boolean isFollowing(long follower, long followee);
}
