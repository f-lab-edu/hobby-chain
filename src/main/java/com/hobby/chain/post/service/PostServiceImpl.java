package com.hobby.chain.post.service;

import com.hobby.chain.member.exception.ForbiddenException;
import com.hobby.chain.member.exception.NotExistUserException;
import com.hobby.chain.post.domain.mapper.FileMapper;
import com.hobby.chain.post.domain.mapper.PostMapper;
import com.hobby.chain.post.dto.ImageDTO;
import com.hobby.chain.post.dto.PostDTO;
import com.hobby.chain.post.dto.ResponsePost;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class PostServiceImpl implements PostService{
    private final PostMapper mapper;
    private final FileService fileService;
    private final FileMapper fileMapper;

    public PostServiceImpl(PostMapper mapper, FileService fileService, FileMapper fileMapper) {
        this.mapper = mapper;
        this.fileService = fileService;
        this.fileMapper = fileMapper;
    }

    @Override
    public void uploadNewPost(long userId, String content, List<MultipartFile> images) {
        if (userId == 0) throw new ForbiddenException();

        mapper.insertPost(PostDTO.builder()
                .userIdx(userId)
                .postContent(content).build());

        long postId = mapper.getLatestId();

        if (images != null && !images.isEmpty()){
            if(images.size() == 1){
                ImageDTO imageDTO = fileService.uploadFile(images.get(0), postId);
                fileMapper.uploadImage(imageDTO);
            } else {
                List<ImageDTO> imageDTOS = fileService.uploadFiles(images, postId);
                fileMapper.uploadImages(imageDTOS);
            }
        }
    }

    @Override
    public PostDTO getPost(long postId) {
        boolean existsImage = mapper.isExistsImage(postId);

        if(existsImage){
            return mapper.getPostWithImage(postId);
        } else {
            return mapper.getPostWithoutImage(postId);
        }
    }

    @Override
    public List<ResponsePost> getPosts(long currentSeq) {
        long startIdx = mapper.getLatestId() - (currentSeq*15);
        return mapper.getPosts(startIdx);
    }

    private long getTotalCount(){
        return mapper.getTotalCount();
    }
}
