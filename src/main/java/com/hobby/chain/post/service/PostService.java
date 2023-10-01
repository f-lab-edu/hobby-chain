package com.hobby.chain.post.service;

import com.hobby.chain.post.dto.PostDTO;
import com.hobby.chain.post.dto.ResponsePost;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PostService {
    public void uploadNewPost(long UserIdx, String content, List<MultipartFile> images);
    public List<ResponsePost> getPosts(long startIdx);
    public PostDTO getPost(long postId);
}
