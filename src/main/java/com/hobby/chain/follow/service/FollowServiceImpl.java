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

import java.util.List;
import java.util.Map;

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
        long follower = getLoginUser();
        isExistUserCheck(followee);

        boolean following = isFollowing(follower, followee);
        if(!following){
            followMapper.deleteFollow(follower, followee);
        } else {
            throw new AlreadyFollowingException();
        }
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void unsubscribe(long followee) {
        long follower = getLoginUser();
        isExistUserCheck(followee);

        boolean following = isFollowing(follower, followee);
        if(following){
            followMapper.deleteFollow(follower, followee);
        } else {
            throw new NotFollowingUserException();
        }
    }

    private long getLoginUser() throws ForbiddenException{
        return loginService.getLoginMemberIdx();
    }

    private void isExistUserCheck(long userId) throws NotExistUserException{
        boolean existUser = memberService.isExistUser(userId);
        if(!existUser) throw new NotExistUserException();
    }

    @Override
    public boolean isFollowing(long follower, long followee) {
        return followMapper.isFollowing(follower, followee);
    }

    @Override
    public long getFolloweeCountByUserId(long userId) {
        isExistUserCheck(userId);
        return followMapper.getFolloweeCountByUserId(userId);
    }

    @Override
    public List<Map<String, Long>> getFolloweeByUserId(long userId) {
        isExistUserCheck(userId);
        return followMapper.getFolloweeByUserId(userId);
    }

    @Override
    public List<String> getFolloweeIds(long userId) {
        return followMapper.getFolloweeIds(userId);
    }

    @Override
    public long getFollowerCountByUserId(long userId) {
        isExistUserCheck(userId);
        return followMapper.getFollowerCountByUserId(userId);
    }

    @Override
    public List<Map<String, Long>> getFollowerByUserId(long userId) {
        isExistUserCheck(userId);
        return followMapper.getFollowerByUserId(userId);
    }

}
