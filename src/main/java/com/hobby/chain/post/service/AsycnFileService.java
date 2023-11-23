package com.hobby.chain.post.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.hobby.chain.post.dto.ImageDTO;
import com.hobby.chain.post.exception.FileUploadFailException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
public class AsycnFileService implements AsyncService{
    @Value("${naver.objectStorage.bucketName}")
    private String bucketName;

    private final AmazonS3 awsS3Client;

    public AsycnFileService(AmazonS3 awsS3Client) {
        this.awsS3Client = awsS3Client;
    }

    @Async
    public CompletableFuture<ImageDTO> uploadAndToDto(MultipartFile file, long postId) {
        String fileName = file.getOriginalFilename();
        String uuid = UUID.randomUUID().toString();
        String extension = fileName != null ? fileName.substring(fileName.lastIndexOf(".")) : null;
        String objectKey = uuid + extension;
        long fileSize = file.getSize();

        try{
            byte[] fileData = file.getBytes();
            uploadToBucket(objectKey, fileData);
            return createImageDTO(postId, uuid, fileSize, objectKey);
        } catch (IOException e) {
            throw new FileUploadFailException("버킷에 파일 업로드 중 실패하였습니다.");
        }
    }

    private void uploadToBucket(String objectKey, byte[] fileData){
        awsS3Client.putObject(new PutObjectRequest(
                bucketName, objectKey,
                new ByteArrayInputStream(fileData), new ObjectMetadata()));
    }

    private CompletableFuture<ImageDTO> createImageDTO(long postId, String uuid, long fileSize, String objectKey) {
        return CompletableFuture.supplyAsync(() -> ImageDTO.builder()
                .postId(postId)
                .imageId(uuid)
                .fileSize(fileSize)
                .imagePath(objectKey)
                .build());
    }
}
