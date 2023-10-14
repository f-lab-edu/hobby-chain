package com.hobby.chain.follow.service;

import com.hobby.chain.follow.domain.mapper.FollowMapper;
import com.hobby.chain.follow.exception.AlreadyFollowingException;
import com.hobby.chain.follow.exception.NotFollowingUserException;
import com.hobby.chain.member.exception.ForbiddenException;
import com.hobby.chain.member.exception.NotExistUserException;
import com.hobby.chain.member.service.MemberLoginService;
import com.hobby.chain.member.service.MemberService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FollowServiceImpl implements FollowService{
    private final FollowMapper followMapper;
    private final MemberService memberService;
    private final MemberLoginService loginService;

    public FollowServiceImpl(FollowMapper followMapper, MemberService memberService, MemberLoginService loginService) {
        this.followMapper = followMapper;
        this.memberService = memberService;
        this.loginService = loginService;
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void subscribe(long followee) {
        checkAndFollowOrUnfollow(followee, true);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void unsubscribe(long followee) {
        checkAndFollowOrUnfollow(followee, false);
    }

    private void checkAndFollowOrUnfollow(long followee, boolean isSubscribe){
        long follower = loginService.getLoginMemberIdx();
        loginCheck(follower);

        boolean existUser = memberService.isExistUser(followee);
        boolean following = isFollowing(follower, followee);

        if(existUser) {
            if (!following && isSubscribe) {
                followMapper.insertFollow(follower, followee);
            } else if (following && !isSubscribe) {
                followMapper.deleteFollow(follower, followee);
            } else {
                throw new AlreadyFollowingException();
            }
        } else {
            throw new NotExistUserException();
        }
    }

    @Override
    public boolean isFollowing(long follower, long followee) {
        return followMapper.isFollowing(follower, followee);
    }

    private void loginCheck(long userId) throws ForbiddenException{
        long loginMemberIdx = loginService.getLoginMemberIdx();
        if(userId != loginMemberIdx){
            throw new ForbiddenException("로그인이 필요한 기능입니다.");
        }
    }

}
