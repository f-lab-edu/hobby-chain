package com.hobby.chain.post.service;

import com.hobby.chain.post.dto.ImageDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.concurrent.CompletableFuture;

public interface AsyncService {
    public CompletableFuture<ImageDTO> uploadAndToDto(MultipartFile file, long postId);
}
