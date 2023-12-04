package com.hobby.chain.follow.domain.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface FollowMapper {
    void insertFollow(@Param("follower") long follower, @Param("followee") long followee);
    void deleteFollow(@Param("follower") long follower, @Param("followee") long followee);
    boolean isFollowing(@Param("follower") long follower, @Param("followee") long followee);
    long getFolloweeCountByUserId(@Param("followerId") long followerId);
    List<Map<String, Long>> getFolloweeByUserId(@Param("followerId") long followerId);
    List<String> getFolloweeIds(@Param("followerId") long followerId);
    long getFollowerCountByUserId(@Param("followeeId") long followerId);
    List<Map<String, Long>> getFollowerByUserId(@Param("followeeId") long followerId);
}
