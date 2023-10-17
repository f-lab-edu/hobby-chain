package com.hobby.chain.post.service;

import com.hobby.chain.post.dto.ResponsePost;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PostService {
    public void uploadNewPost(long UserIdx, String content, List<MultipartFile> images);
    public List<ResponsePost> getPosts(long startIdx);
    public ResponsePost getPost(long postId);
    public void deletePost(long userId, long postId);
    void updatePost(long userId, long postId, String content);
    boolean isExistsPost(long postId);
}
