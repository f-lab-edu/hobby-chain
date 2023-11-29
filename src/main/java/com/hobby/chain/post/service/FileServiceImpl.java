package com.hobby.chain.post.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.hobby.chain.post.domain.mapper.FileMapper;
import com.hobby.chain.post.dto.ImageDTO;
import com.hobby.chain.post.exception.FileUploadFailException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;

@Service
public class FileServiceImpl implements FileService{
    @Value("${naver.objectStorage.bucketName}")
    private String bucketName;

    private final AmazonS3 awsS3Client;
    private final FileMapper fileMapper;
    private final Executor customExecutor;

    public FileServiceImpl(AmazonS3 awsS3Client, FileMapper fileMapper, @Qualifier("asyncExecutor") Executor customExecutor) {
        this.awsS3Client = awsS3Client;
        this.fileMapper = fileMapper;
        this.customExecutor = customExecutor;
    }

    @Override
    public void uploadFiles(List<MultipartFile> files, long postId){
        files.forEach(file -> uploadAndToDto(file, postId));
    }

    public void uploadAndToDto(MultipartFile file, long postId) {
        CompletableFuture.runAsync(() -> {
            String fileName = file.getOriginalFilename();
            String uuid = UUID.randomUUID().toString();
            String extension = fileName != null ? fileName.substring(fileName.lastIndexOf(".")) : null;
            String objectKey = uuid + extension;
            long fileSize = file.getSize();

            try{
                byte[] fileData = file.getBytes();
                uploadToBucket(objectKey, fileData);
                uploadToDB(postId, uuid, fileSize, objectKey);
            } catch (IOException e) {
                throw new FileUploadFailException("버킷에 파일 업로드 중 실패하였습니다.");
            }
        }, customExecutor);
    }

    private void uploadToBucket(String objectKey, byte[] fileData){
        CompletableFuture.runAsync(() -> {
            ObjectMetadata data = new ObjectMetadata();
            data.setContentLength(fileData.length);

            awsS3Client.putObject(new PutObjectRequest(
                    bucketName, objectKey,
                    new ByteArrayInputStream(fileData), data));
        }, customExecutor);
    }

    private void uploadToDB(long postId, String uuid, long fileSize, String objectKey) {
        CompletableFuture.runAsync(() -> {
            fileMapper.uploadImage(ImageDTO.builder()
                    .postId(postId)
                    .imageId(uuid)
                    .fileSize(fileSize)
                    .imagePath(objectKey)
                    .build());
        }, customExecutor);
    }
}
