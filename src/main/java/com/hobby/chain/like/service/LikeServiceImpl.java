package com.hobby.chain.like.service;

import com.hobby.chain.like.domain.mapper.LikeMapper;
import com.hobby.chain.like.exception.AlreadyLikeException;
import com.hobby.chain.member.exception.NotExistUserException;
import com.hobby.chain.member.service.MemberLoginService;
import com.hobby.chain.member.service.MemberService;
import org.springframework.stereotype.Service;

@Service
public class LikeServiceImpl implements LikeService{
    private final LikeMapper likeMapper;
    private final MemberLoginService loginService;
    private final MemberService memberService;

    public LikeServiceImpl(LikeMapper likeMapper, MemberLoginService loginService, MemberService memberService) {
        this.likeMapper = likeMapper;
        this.loginService = loginService;
        this.memberService = memberService;
    }

    @Override
    public void like(long postId) {
        checkAndLikeOrUnlike(postId, true);
    }

    @Override
    public void unlike(long postId) {
        checkAndLikeOrUnlike(postId, false);
    }

    @Override
    public int getLikeCount(long postId) {
        return 0;
    }

    private void checkAndLikeOrUnlike(long postId, boolean isLikeRequest){
        long loginUser = loginService.getLoginMemberIdx();

        boolean existUser = memberService.exist(postId + "");
        boolean isLike = isLike(postId, loginUser);

        if (existUser){
            if (!isLike && isLikeRequest){
                likeMapper.insertLike(loginUser, postId);
            } else if (isLike && !isLikeRequest) {
                likeMapper.deleteLike(loginUser, postId);
            } else {
                throw new AlreadyLikeException();
            }
        } else {
            throw new NotExistUserException();
        }
    }

    private boolean isLike(long postId, long userId) {
        return likeMapper.isLike(postId, userId);
    }
}
