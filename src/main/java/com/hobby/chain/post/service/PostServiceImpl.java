package com.hobby.chain.post.service;

import com.hobby.chain.follow.service.FollowService;
import com.hobby.chain.member.exception.ForbiddenException;
import com.hobby.chain.member.service.MemberService;
import com.hobby.chain.post.domain.mapper.FileMapper;
import com.hobby.chain.post.domain.mapper.PostMapper;
import com.hobby.chain.post.dto.ImageDTO;
import com.hobby.chain.post.dto.ResponsePost;
import com.hobby.chain.post.exception.FileUploadFailException;
import com.hobby.chain.post.exception.NoExistsPost;
import com.hobby.chain.push.PushType;
import com.hobby.chain.push.service.LocaleService;
import com.hobby.chain.push.service.ProducerService;
import org.springframework.context.MessageSource;
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
        long postId = insertPost(userId, content);

        if (CollectionUtils.isEmpty(images)){
            //두번째 트랜잭션 - 파일 업로드
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
    public void uploadImages(List<MultipartFile> images, long postId) throws FileUploadFailException{
        List<ImageDTO> imageDTOS = toDTOS(images, postId);
        fileMapper.uploadImages(imageDTOS);
    }

    private List<ImageDTO> toDTOS(List<MultipartFile> images, long postId) throws FileUploadFailException {
         return fileService.uploadFiles(images, postId);
    }

    @Override
    public ResponsePost getPost(long postId) {
        boolean isExistsPost = mapper.isExistsPost(postId);

        if(isExistsPost) {
            return mapper.getPost(postId);
        } else {
            throw new NoExistsPost("게시물이 존재하지 않습니다.");
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

    private void pushMessage(long userId){
        List<String> ids = followService.getFolloweeIds(userId);

        String nickname = memberService.getNicknameById(userId);

        String pushMessage =
                messageSource.getMessage(
                        "push.message.post", new String[] {nickname},
                        localeService.getCurrentLocale());

        producerService.sendMessageToQueue(ids, PushType.NEW_POST, pushMessage);
    }
}
