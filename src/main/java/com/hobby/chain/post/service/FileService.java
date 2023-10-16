package com.hobby.chain.post.service;

import com.hobby.chain.post.dto.ImageDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileService {
    public List<ImageDTO> uploadFiles(List<MultipartFile> files, long postId);
}
