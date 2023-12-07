package com.hobby.chain.like.service;

import com.hobby.chain.like.domain.mapper.LikeMapper;
import com.hobby.chain.like.exception.AlreadyLikeException;
import com.hobby.chain.member.exception.ForbiddenException;
import com.hobby.chain.member.service.MemberLoginService;
import com.hobby.chain.post.exception.NoExistsPost;
import com.hobby.chain.post.service.PostService;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LikeServiceImpl implements LikeService{
    private final LikeMapper likeMapper;
    private final MemberLoginService loginService;
    private final PostService postService;

    public LikeServiceImpl(LikeMapper likeMapper, MemberLoginService loginService, PostService postService) {
        this.likeMapper = likeMapper;
        this.loginService = loginService;
        this.postService = postService;
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void like(long postId) {
        checkExistsPost(postId);

        long userId = getLoginUser();

        try{
            likeMapper.insertLike(postId, userId);
        } catch (DuplicateKeyException de){
            throw new AlreadyLikeException();
        }


    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void unlike(long postId) {
        checkExistsPost(postId);

        boolean like = isLike(postId);
        if(like) {
            likeMapper.deleteLike(postId, getLoginUser());
        } else {
            throw new ForbiddenException("좋아요를 삭제할 권한이 없습니다.");
        }
    }

    @Override
    public long getLikeCount(long postId) {
        return likeMapper.getLikeByPostId(postId);
    }

    private void checkExistsPost(long postId) throws NoExistsPost{
        boolean existsPost = true; //postService.isExistsPost(postId);
        if(!existsPost) throw new NoExistsPost();
    }

    private boolean isLike(long postId) {
        long userId = getLoginUser();
        return likeMapper.isLike(postId, userId);
    }

    private long getLoginUser(){
        return loginService.getLoginMemberIdx();
    }
}
