package com.hobby.chain.post.service;

import com.hobby.chain.post.dto.PostDTO;

public interface PostService {
    public void uploadNewPost(long UserId, PostDTO postDTO);
    public PostDTO getAllPost();
    public PostDTO getPost(long postId);
}
