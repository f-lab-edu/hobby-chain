package com.hobby.chain.like.service;

public interface LikeService {
    public void like(long postId);
    public void unlike(long postId);
    public int getLikeCount(long postId);
}
