package com.hobby.chain.post.service;

import com.hobby.chain.post.dto.PostDTO;

import java.util.List;

public interface PostService {
    public void uploadNewPost(long UserId, PostDTO postDTO);
    public List<PostDTO> getAllPost();
    public PostDTO getPost(long postId);
}
