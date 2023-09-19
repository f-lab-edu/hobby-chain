package com.hobby.chain.post.domain.mapper;

import com.hobby.chain.post.dto.ImageDTO;
import com.hobby.chain.post.dto.PostDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PostMapper {
    void insertPost(PostDTO postDTO);
    void insertImages(ImageDTO imageDTO);
    boolean isExistsImage(long postId);
    PostDTO getAllPost();
    PostDTO getPostWithImage(long postId);
    PostDTO getPostWithoutImage(long postId);

}
