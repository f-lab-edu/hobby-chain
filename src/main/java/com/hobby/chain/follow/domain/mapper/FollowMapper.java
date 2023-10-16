package com.hobby.chain.follow.domain.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface FollowMapper {
    void insertFollow(@Param("follower") long follower, @Param("followee") long followee);
    void deleteFollow(@Param("follower") long follower, @Param("followee") long followee);
    boolean isFollowing(@Param("follower") long follower, @Param("followee") long followee);
}
