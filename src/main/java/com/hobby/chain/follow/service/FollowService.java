package com.hobby.chain.follow.service;

import java.util.List;
import java.util.Map;

public interface FollowService {
    public void subscribe(Long follower, long followee);
    public void unsubscribe(Long follower, long followee);
    public boolean isFollowing(long follower, long followee);
    public long getFolloweeCountByUserId(long userId);
    public List<Map<String, Long>> getFolloweeByUserId(long userId);
    public long getFollowerCountByUserId(long userId);
    public List<Map<String, Long>> getFollowerByUserId(long userId);
}
