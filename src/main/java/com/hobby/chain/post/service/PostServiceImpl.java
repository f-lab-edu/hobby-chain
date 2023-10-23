package com.hobby.chain.post.service;

import com.hobby.chain.member.exception.ForbiddenException;
import com.hobby.chain.member.service.MemberLoginService;
import com.hobby.chain.post.domain.mapper.FileMapper;
import com.hobby.chain.post.domain.mapper.PostMapper;
import com.hobby.chain.post.dto.ImageDTO;
import com.hobby.chain.post.dto.ResponsePost;
import com.hobby.chain.post.exception.FileUploadFailException;
import com.hobby.chain.post.exception.NoExistException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class PostServiceImpl implements PostService{
    private final PostMapper mapper;
    private final FileService fileService;
    private final FileMapper fileMapper;
    private final MemberLoginService loginService;

    public PostServiceImpl(PostMapper mapper, FileService fileService, FileMapper fileMapper, MemberLoginService loginService) {
        this.mapper = mapper;
        this.fileService = fileService;
        this.fileMapper = fileMapper;
        this.loginService = loginService;
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void uploadNewPost(long userId, String content, List<MultipartFile> images) {
        if (userId != loginService.getLoginMemberIdx()) throw new ForbiddenException();

        long postId = insertPost(userId, content);

        if (!CollectionUtils.isEmpty(images)){
            uploadImages(images, postId);
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public long insertPost(long userId, String content){
        mapper.insertPost(userId, content);
        return mapper.getLatestId();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void uploadImages(List<MultipartFile> images, long postId) throws FileUploadFailException{
        List<ImageDTO> imageDTOS = toDTOS(images, postId);
        fileMapper.uploadImages(imageDTOS);
    }

    private List<ImageDTO> toDTOS(List<MultipartFile> images, long postId) throws FileUploadFailException {
         return fileService.uploadFiles(images, postId);
    }

    @Override
    public ResponsePost getPost(long postId) {
        if(isExistsPost(postId)) {
            return mapper.getPost(postId);
        } else {
            throw new NoExistException("게시물이 존재하지 않습니다.");
        }
    }

    @Override
    public List<ResponsePost> getPosts(long currentSeq) {
        long startIdx = mapper.getLatestId() - (currentSeq*15);
        return mapper.getPosts(startIdx);
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void updatePost(long userId, long postId, String content) {
        if(isauthorizedOnPost(userId, postId)){
            mapper.updatePost(content, postId);
        } else{
            throw new ForbiddenException("게시물을 수정할 권한이 없습니다.");
        }
    }

    @Override
    public boolean isExistsPost(long postId) {
        return mapper.isExistsPost(postId);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void deletePost(long userId, long postId) {
        if(isauthorizedOnPost(userId, postId)){
            if(mapper.isExistsImage(postId)){
                fileMapper.deleteImages(postId);
            }
            mapper.deletePost(postId);
        } else{
            throw new ForbiddenException("게시물을 삭제할 권한이 없습니다.");
        }
    }

    private boolean isauthorizedOnPost(long userId, long postId){
        return mapper.isAuthorizedOnPost(userId, postId);
    }

    private long getTotalCount(){
        return mapper.getTotalCount();
    }
}
