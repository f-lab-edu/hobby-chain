package com.hobby.chain.post.domain.mapper;

import com.hobby.chain.post.dto.ImageDTO;
import com.hobby.chain.post.dto.PostDTO;
import com.hobby.chain.post.dto.ResponsePost;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PostMapper {
    void insertPost(@Param("userId") long userId, @Param("postContent") String postContent);
    boolean isExistsPost(long postId);
    boolean isExistsImage(long postId);
    List<ResponsePost> getPosts(long startIdx);
    ResponsePost getPost(long postId);
    long getLatestId();
    long getTotalCount();
    boolean isAuthorizedOnPost(@Param("userId") long userId, @Param("postId") long postId);
    void updatePost(@Param("postContent") String content, @Param("postId") long postId);
    void deletePost(long postId);

}
