package com.hobby.chain.post.service;

import com.hobby.chain.follow.service.FollowService;
import com.hobby.chain.member.exception.ForbiddenException;
import com.hobby.chain.member.service.MemberService;
import com.hobby.chain.post.domain.mapper.FileMapper;
import com.hobby.chain.post.domain.mapper.PostMapper;
import com.hobby.chain.post.dto.ImageDTO;
import com.hobby.chain.post.dto.ResponsePost;
import com.hobby.chain.post.exception.FileUploadFailException;
import com.hobby.chain.push.PushType;
import com.hobby.chain.push.service.LocaleService;
import com.hobby.chain.push.service.ProducerService;
import org.springframework.context.MessageSource;
import com.hobby.chain.post.exception.NoExistException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class PostServiceImpl implements PostService{
    private final PostMapper mapper;
    private final FileService fileService;
    private final FileMapper fileMapper;
    private final MemberService memberService;
    private final FollowService followService;
    private final ProducerService producerService;
    private final MessageSource messageSource;
    private final LocaleService localeService;

    public PostServiceImpl(PostMapper mapper, FileService fileService, FileMapper fileMapper, MemberService memberService, FollowService followService, ProducerService producerService, MessageSource messageSource, LocaleService localeService) {
        this.mapper = mapper;
        this.fileService = fileService;
        this.fileMapper = fileMapper;
        this.memberService = memberService;
        this.followService = followService;
        this.producerService = producerService;
        this.messageSource = messageSource;
        this.localeService = localeService;
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void uploadNewPost(long userId, String content, List<MultipartFile> images) {
        //첫 트랜잭션 - 포스트 업로드
        if (userId != loginService.getLoginMemberIdx()) throw new ForbiddenException();

        long postId = insertPost(userId, content);

        if (!CollectionUtils.isEmpty(images)){
            uploadImages(images, postId);
        }

        pushMessage(userId);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public long insertPost(long userId, String content){
        mapper.insertPost(userId, content);
        return mapper.getLatestId();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void uploadImages(List<MultipartFile> images, long postId){
        fileService.uploadFiles(images, postId);
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
    public List<ResponsePost> getPosts(long start) {
        long startIdx = mapper.getLatestId() - start;
        return mapper.getPosts(startIdx);
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void updatePost(long userId, long postId, String content) {
        if(isAuthorizedOnPost(userId, postId)){
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
        if(isAuthorizedOnPost(userId, postId)){
            if(mapper.isExistsImage(postId)){
                fileMapper.deleteImages(postId);
            }
            mapper.deletePost(postId);
        } else{
            throw new ForbiddenException("게시물을 삭제할 권한이 없습니다.");
        }
    }


    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void pushMessage(long userId){
        List<String> ids = followService.getFolloweeIds(userId); // 알림 보낼 id
        String nickname = memberService.getNicknameById(userId); // 게시글을 작성한 유저 닉네임

        String pushMessage =
                messageSource.getMessage(
                        "push.message.post", new String[] {nickname},
                        localeService.getCurrentLocale());

        producerService.sendMessageToQueue(ids, PushType.NEW_POST, pushMessage);
    }
  
    private boolean isAuthorizedOnPost(long userId, long postId){
        return mapper.isAuthorizedOnPost(userId, postId);
    }

    private long getTotalCount(){
        return mapper.getTotalCount();
    }
}
