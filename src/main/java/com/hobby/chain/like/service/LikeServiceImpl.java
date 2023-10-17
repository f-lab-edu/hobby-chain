package com.hobby.chain.like.service;

import com.hobby.chain.like.domain.mapper.LikeMapper;
import com.hobby.chain.like.exception.AlreadyLikeException;
import com.hobby.chain.member.exception.NotExistUserException;
import com.hobby.chain.member.service.MemberLoginService;
import com.hobby.chain.member.service.MemberService;
import com.hobby.chain.post.exception.NoExistsPost;
import com.hobby.chain.post.service.PostService;
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
        checkAndLikeOrUnlike(postId, true);
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void unlike(long postId) {
        checkAndLikeOrUnlike(postId, false);
    }

    @Override
    public int getLikeCount(long postId) {
        return likeMapper.getLikeByPostId(postId);
    }

    private void checkAndLikeOrUnlike(long postId, boolean isLikeRequest){
        long loginUser = loginService.getLoginMemberIdx();
        loginService.loginCheck(loginUser);

        boolean existsPost = postService.isExistsPost(postId);
        boolean isLike = isLike(postId, loginUser);

        if (existsPost){
            if (!isLike && isLikeRequest){
                likeMapper.insertLike(loginUser, postId);
            } else if (isLike && !isLikeRequest) {
                likeMapper.deleteLike(loginUser, postId);
            } else {
                throw new AlreadyLikeException();
            }
        } else {
            throw new NoExistsPost();
        }
    }

    private boolean isLike(long postId, long userId) {
        return likeMapper.isLike(postId, userId);
    }
}
