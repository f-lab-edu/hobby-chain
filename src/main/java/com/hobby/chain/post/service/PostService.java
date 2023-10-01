package com.hobby.chain.post.service;

import com.hobby.chain.post.dto.PostDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PostService {
    public void uploadNewPost(long UserIdx, String content, List<MultipartFile> images);
    public List<PostDTO> getPosts();
    public PostDTO getPost(long postId);
}
