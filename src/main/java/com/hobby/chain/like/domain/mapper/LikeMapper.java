package com.hobby.chain.like.domain.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface LikeMapper {
    boolean isLike(@Param("postId") long postId, @Param("userId") long userId);
    void insertLike(@Param("userId") long userId, @Param("postId") long postId);
    void deleteLike(@Param("userId") long userId, @Param("postId") long postId);
}
