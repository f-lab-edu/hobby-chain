package com.hobby.chain.post.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.hobby.chain.post.dto.ImageDTO;
import com.hobby.chain.post.exception.FileUploadFailException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class FileServiceImpl implements FileService{
    @Value("${naver.objectStorage.bucketName}")
    private String bucketName;

    private final AmazonS3 awsS3Client;

    public FileServiceImpl(AmazonS3 awsS3Client) {
        this.awsS3Client = awsS3Client;
    }

    @Override
    public List<ImageDTO> uploadFiles(List<MultipartFile> files, long postId) throws FileUploadFailException {
         return files.stream()
                 .map(file -> uploadAndToDto(file, postId))
                 .collect(Collectors.toList());
    }

    private ImageDTO uploadAndToDto(MultipartFile file, long postId) throws FileUploadFailException {
        String fileName = file.getOriginalFilename();
        String uuid = UUID.randomUUID().toString();
        String extension = fileName != null ? fileName.substring(fileName.lastIndexOf(".")) : null;
        String objectKey = uuid + extension;
        long fileSize = file.getSize();

        try{
            byte[] fileData = file.getBytes();
            uploadToBucket(objectKey, fileData);
            return createImageDTO(uuid, fileName, fileSize, objectKey);
        } catch (IOException e) {
            throw new FileUploadFailException("버킷에 파일 업로드 중 실패하였습니다.");
        }
        }

    private void uploadToBucket(String objectKey, byte[] fileData){
        awsS3Client.putObject(new PutObjectRequest(
                bucketName, objectKey,
                new ByteArrayInputStream(fileData), new ObjectMetadata()));
    }

    private ImageDTO createImageDTO(String uuid, String fileName, long fileSize, String objectKey) {
        return ImageDTO.builder()
                .imageId(uuid)
                .imageName(fileName)
                .fileSize(fileSize)
                .imagePath(objectKey)
                .build();
    }
}
