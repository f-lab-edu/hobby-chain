package com.hobby.chain.follow.service;

import java.util.List;
import java.util.Map;

public interface FollowService {
    public void subscribe(long followee);
    public void unsubscribe(long followee);
    public boolean isFollowing(long follower, long followee);
    public long getFolloweeCountByUserId(long userId);
    public List<Map<String, Long>> getFolloweeByUserId(long userId);
}
