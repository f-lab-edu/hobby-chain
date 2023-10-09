package com.hobby.chain.follow.service;

import com.hobby.chain.follow.exception.AlreadyFollowingException;
import com.hobby.chain.follow.exception.NotFollowingUserException;
import com.hobby.chain.member.Gender;
import com.hobby.chain.member.domain.mapper.MemberMapper;
import com.hobby.chain.member.dto.MemberDTO;
import com.hobby.chain.member.exception.ForbiddenException;
import com.hobby.chain.member.exception.NotExistUserException;
import com.hobby.chain.member.service.MemberLoginService;
import com.hobby.chain.member.service.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class FollowServiceImplTest {
    private final MemberService memberService;
    private final MemberLoginService loginService;
    private final FollowService followService;
    private final MemberMapper memberMapper;

    @Autowired
    public FollowServiceImplTest(MemberService memberService, MemberLoginService loginService, FollowService followService, MemberMapper memberMapper) {
        this.memberService = memberService;
        this.loginService = loginService;
        this.followService = followService;
        this.memberMapper = memberMapper;
    }

    private MemberDTO testFolloweeUser;
    @BeforeEach
    void testMemberSetUp(){
        MemberDTO memberDTO = MemberDTO.builder()
                .email("qpqp7371@gmail.com")
                .password("xxxx1234*")
                .name("정서현")
                .nickName("서현")
                .phoneNumber("010-4600-4123")
                .gender(Gender.M)
                .birth("20040227")
                .build();
        memberService.signUp(memberDTO);

        loginService.login(memberDTO.getEmail(), memberDTO.getPassword());

        testFolloweeUser = MemberDTO.builder()
                .email("qpqp7372@gmail.com")
                .password("xxxx1234*")
                .name("정서현")
                .nickName("서현")
                .phoneNumber("010-4600-4123")
                .gender(Gender.M)
                .birth("20040227")
                .build();
        memberService.signUp(testFolloweeUser);
    }

    @Test
    void 팔로우_정상_테스트() {
        //given
        long follower = loginService.getLoginMemberIdx();
        long followee = memberMapper.findById(testFolloweeUser.getEmail()).getUserId();

        //when
        followService.subscribe(follower, followee);

        //then
        assertThat(followService.isFollowing(follower, followee)).isTrue();
    }
    @Test
    @DisplayName("이미 팔로우 된 상황")
    void 중복_팔로우_테스트() {
        //given
        long follower = loginService.getLoginMemberIdx();
        long followee = memberMapper.findById(testFolloweeUser.getEmail()).getUserId();
        followService.subscribe(follower, followee);

        //when
        AlreadyFollowingException ae = assertThrows(AlreadyFollowingException.class,
                () -> followService.subscribe(follower, followee));

        //then
        assertThat(ae.getClass()).isEqualTo(AlreadyFollowingException.class);
    }

    @Test
    @DisplayName("followee가 없는 아이디였을 때 상황")
    void 팔로우_테스트_followeeX() {
        //given
        long follower = loginService.getLoginMemberIdx();
        long followee = 0L;

        //when
        NotExistUserException ne = assertThrows(NotExistUserException.class, () -> followService.subscribe(follower, followee));

        //then
        assertThat(ne.getClass()).isEqualTo(NotExistUserException.class);
    }

    @Test
    @DisplayName("로그인하지 않았을 때 상황")
    void 팔로우_테스트_followerX(){
        //given
        long follower = 0L;
        long followee = memberMapper.findById(testFolloweeUser.getEmail()).getUserId();

        //when
        ForbiddenException fe = assertThrows(ForbiddenException.class, () -> followService.subscribe(follower, followee));

        //then
        assertThat(fe.getClass()).isEqualTo(ForbiddenException.class);
    }

    @Test
    void 언팔로우_정상_테스트() {
        //given
        long follower = loginService.getLoginMemberIdx();
        long followee = memberMapper.findById(testFolloweeUser.getEmail()).getUserId();
        followService.subscribe(follower, followee);

        //when
        followService.unsubscribe(follower, followee);

        //then
        assertThat(followService.isFollowing(follower, followee)).isFalse();
    }

    @Test
    @DisplayName("로그인하지 않았을 때 상황")
    void 언팔로우_테스트_followerX() {
        //given
        long follower = 0L;
        long followee = memberMapper.findById(testFolloweeUser.getEmail()).getUserId();

        //when
        ForbiddenException fe = assertThrows(ForbiddenException.class,
                () -> followService.unsubscribe(follower, followee));

        //then
        assertThat(fe.getMessage()).isEqualTo("로그인이 필요한 기능입니다.");
    }

    @Test
    @DisplayName("팔로잉하지 않았을 때 상황")
    void 언팔로우_테스트_subX() {
        //given
        long follower = loginService.getLoginMemberIdx();
        long followee = memberMapper.findById(testFolloweeUser.getEmail()).getUserId();

        //when
        NotFollowingUserException ne = assertThrows(NotFollowingUserException.class, () -> followService.unsubscribe(follower, followee));

        //then
        assertThat(ne.getClass()).isEqualTo(NotFollowingUserException.class);
    }
}