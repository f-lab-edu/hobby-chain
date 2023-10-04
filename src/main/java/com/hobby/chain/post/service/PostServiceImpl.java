package com.hobby.chain.post.service;

import com.hobby.chain.member.exception.ForbiddenException;
import com.hobby.chain.post.domain.mapper.FileMapper;
import com.hobby.chain.post.domain.mapper.PostMapper;
import com.hobby.chain.post.dto.ImageDTO;
import com.hobby.chain.post.dto.ResponsePost;
import com.hobby.chain.post.exception.NotExistsPost;
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

        mapper.insertPost(userId, content);

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
    public ResponsePost getPost(long postId) {
        boolean isExistsPost = mapper.isExistsPost(postId);

        if(isExistsPost) {
            return mapper.getPost(postId);
        } else {
            throw new NotExistsPost("게시물이 존재하지 않습니다.");
        }
    }

    @Override
    public List<ResponsePost> getPosts(long currentSeq) {
        long startIdx = mapper.getLatestId() - (currentSeq*15);
        return mapper.getPosts(startIdx);
    }

    @Override
    public void updatePost(long userId, long postId, String content) {
        boolean authorizedOnPost = mapper.isAuthorizedOnPost(userId, postId);
        if(authorizedOnPost){
            mapper.updatePost(content, postId);
        } else{
            throw new ForbiddenException("게시물을 수정할 권한이 없습니다.");
        }
    }

    @Override
    public void deletePost(long userId, long postId) {
        boolean authorizedOnPost = mapper.isAuthorizedOnPost(userId, postId);
        if(authorizedOnPost){
            if(mapper.isExistsImage(postId)){
                fileMapper.deleteImages(postId);
            }
            mapper.deletePost(postId);
        } else{
            throw new ForbiddenException("게시물을 삭제할 권한이 없습니다.");
        }
    }

    private long getTotalCount(){
        return mapper.getTotalCount();
    }
}
