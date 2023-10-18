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
import org.springframework.transaction.annotation.Propagation;
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

    @Transactional(propagation = Propagation.REQUIRED)
    public void checkAndFollowOrUnfollow(long followee, boolean isSubscribe){
        long follower = loginCheck();
        isExistUserCheck(followee);

        boolean following = isFollowing(follower, followee);

        if (following && isSubscribe){
            throw new AlreadyFollowingException();
        } else if (!following && !isSubscribe) {
            throw new NotFollowingUserException();
        }

        if (!following) {
            followMapper.insertFollow(follower, followee);
        } else {
            followMapper.deleteFollow(follower, followee);
        }
    }

    private long loginCheck() throws ForbiddenException{
        return loginService.getLoginMemberIdx();
    }

    private void isExistUserCheck(long userId) throws NotExistUserException{
        memberService.isExistUser(userId);
    }

    @Override
    public boolean isFollowing(long follower, long followee) {
        return followMapper.isFollowing(follower, followee);
    }

}
