package com.hobby.chain.post.service;

import com.hobby.chain.post.dto.ImageDTO;
import com.hobby.chain.post.exception.FileUploadFailException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
public class FileServiceImpl implements FileService{
    @Value("${naver.objectStorage.bucketName}")
    private String bucketName;

    private final AsyncService asycnService;

    public FileServiceImpl(AsyncService asycnService) {
        this.asycnService = asycnService;
    }

    @Override
    public List<ImageDTO> uploadFiles(List<MultipartFile> files, long postId) throws FileUploadFailException {
        List<CompletableFuture<ImageDTO>> futures = new ArrayList<>();

        for (MultipartFile file : files) {
            futures.add(asycnService.uploadAndToDto(file, postId));
        }

        CompletableFuture<Void> allOf = CompletableFuture.allOf(
                futures.toArray(new CompletableFuture[0])
        );

        try {
            allOf.get(); // 모든 비동기 작업이 완료될 때까지 대기
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }

        return futures.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList());
    }
}
