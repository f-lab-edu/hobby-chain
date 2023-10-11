package com.hobby.chain.follow.service;

import com.hobby.chain.follow.domain.mapper.FollowMapper;
import com.hobby.chain.follow.exception.AlreadyFollowingException;
import com.hobby.chain.follow.exception.NotFollowingUserException;
import com.hobby.chain.member.domain.mapper.MemberMapper;
import com.hobby.chain.member.exception.ForbiddenException;
import com.hobby.chain.member.exception.NotExistUserException;
import com.hobby.chain.member.service.MemberLoginService;
import org.springframework.stereotype.Service;

@Service
public class FollowServiceImpl implements FollowService{
    private final MemberMapper memberMapper;
    private final FollowMapper followMapper;
    private final MemberLoginService loginService;

    public FollowServiceImpl(MemberMapper memberMapper, FollowMapper followMapper, MemberLoginService loginService) {
        this.memberMapper = memberMapper;
        this.followMapper = followMapper;
        this.loginService = loginService;
    }

    @Override
    public void subscribe(long follower, long followee) {
        loginCheck(follower);

        boolean existUser = isExistUser(followee);
        boolean following = isFollowing(follower, followee);

        if(existUser){
            if(!following){
                followMapper.insertFollow(follower, followee);
            } else {
                throw new AlreadyFollowingException();
            }
        } else {
            throw new NotExistUserException();
        }
    }

    @Override
    public void unsubscribe(long follower, long followee) {
        loginCheck(follower);

        boolean following = isFollowing(follower, followee);
        if(following){
            followMapper.deleteFollow(follower, followee);
        } else {
            throw new NotFollowingUserException();
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

    private boolean isExistUser(long followeeId){
        return memberMapper.isExistMemberById(followeeId);
    }
}
