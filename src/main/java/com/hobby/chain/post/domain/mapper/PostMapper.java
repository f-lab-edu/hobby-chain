package com.hobby.chain.post.domain.mapper;

import com.hobby.chain.post.dto.ImageDTO;
import com.hobby.chain.post.dto.PostDTO;
import com.hobby.chain.post.dto.ResponsePost;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PostMapper {
    void insertPost(PostDTO postDTO);
    void insertImages(ImageDTO imageDTO);
    boolean isExistsImage(long postId);
    boolean isExistsPost(long memberIdx);
    List<PostDTO> getAllPost();
    List<ResponsePost> getPosts(long startIdx);
    PostDTO getPostWithImage(long postId);
    PostDTO getPostWithoutImage(long postId);
    List<PostDTO> getAllPostWithoutImage();
    long getLatestId();
    long getTotalCount();

}