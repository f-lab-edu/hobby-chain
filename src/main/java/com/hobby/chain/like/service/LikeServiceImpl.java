package com.hobby.chain.like.service;

import com.hobby.chain.like.domain.mapper.LikeMapper;
import com.hobby.chain.like.exception.AlreadyLikeException;
import com.hobby.chain.member.exception.ForbiddenException;
import com.hobby.chain.member.exception.NotExistUserException;
import com.hobby.chain.member.service.MemberLoginService;
import com.hobby.chain.member.service.MemberService;
import com.hobby.chain.post.exception.NoExistsPost;
import com.hobby.chain.post.service.PostService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
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

        boolean like = isLike(postId);
        if(like){
            likeMapper.insertLike(postId, getLoginUser());
        } else {
            throw new ForbiddenException();
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
            throw new ForbiddenException();
        }
    }

    @Override
    public int getLikeCount(long postId) {
        return likeMapper.getLikeByPostId(postId);
    }

    private void checkExistsPost(long postId) throws NoExistsPost{
        boolean existsPost = postService.isExistsPost(postId);
        if(!existsPost) throw new NoExistsPost();
    }

    private boolean isLike(long postId) {
        long userId = loginService.getLoginMemberIdx();
        return likeMapper.isLike(postId, userId);
    }

    private long getLoginUser(){
        return loginService.getLoginMemberIdx();
    }
}
