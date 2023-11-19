package com.hobby.chain.like.service;

public interface LikeService {
    public void like(Long userId, long postId);
    public void unlike(Long userId, long postId);
    public long getLikeCount(long postId);
}
