package com.hobby.chain.post.service;

import com.hobby.chain.post.domain.mapper.FileMapper;
import com.hobby.chain.post.dto.ImageDTO;
import com.hobby.chain.post.exception.FileUploadFailException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class FileServiceImpl implements FileService{
    @Value("$hobby.chain.file.dir:")
    private String fileDir;

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<ImageDTO> uploadFiles(List<MultipartFile> files, long postId) throws FileUploadFailException {
         return files.stream().map(file -> createImageDTO(file, postId)).collect(Collectors.toList());
    }

    private ImageDTO createImageDTO(MultipartFile file, long postId){
        //원래 파일명
        String fileName = file.getOriginalFilename();
        //파일 이름
        String uuid = UUID.randomUUID().toString();
        // 확장자 추출
        String extension = fileName.substring(fileName.lastIndexOf("."));
        // 파일을 불러올 때 사용할 파일 경로
        String savedPath = fileDir + uuid + extension;
        // 파일 사이즈
        long fileSize = file.getSize();

        try {
                file.transferTo(new File(savedPath));
                return ImageDTO.builder().imageId(uuid).imageName(fileName).fileSize(fileSize).imagePath(savedPath).build();
            } catch (IOException e) {
                throw new FileUploadFailException("파일 업로드에 실패했습니다.");
            }
        }
}
