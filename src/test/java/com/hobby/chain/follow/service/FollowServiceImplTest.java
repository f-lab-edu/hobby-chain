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

import java.util.List;
import java.util.Map;

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

    private MemberDTO memberDTO;
    private MemberDTO testFolloweeUser;
    @BeforeEach
    void testMemberSetUp(){
        memberDTO = MemberDTO.builder()
                .email("qpqp7371@gmail.com")
                .password("xxxx1234*")
                .name("정서현")
                .nickName("서현")
                .phoneNumber("010-4600-4123")
                .gender(Gender.M)
                .birth("20040227")
                .build();
        memberService.signUp(memberDTO);

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

    @AfterEach
    void deleteAllData(){
        memberMapper.deleteAll();
    }

    @Test
    void 팔로우_정상_테스트() {
        //given
        long follower = loginService.getLoginMemberIdx();
        long followee = memberMapper.findById(testFolloweeUser.getEmail()).getUserId();
        loginService.login(memberDTO.getEmail(), memberDTO.getPassword());

        //when
        followService.subscribe(follower, followee);

        //then
        assertThat(followService.isFollowing(follower, followee)).isTrue();
    }
    @Test
    @DisplayName("이미 팔로우 된 상황")
    void 중복_팔로우_테스트() {
        //given
        loginService.login(memberDTO.getEmail(), memberDTO.getPassword());
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
        loginService.login(memberDTO.getEmail(), memberDTO.getPassword());
        long followee = 0L;
        long follower = loginService.getLoginMemberIdx();

        //when
        NotExistUserException ne = assertThrows(NotExistUserException.class, () -> followService.subscribe(follower, followee));

        //then
        assertThat(ne.getClass()).isEqualTo(NotExistUserException.class);
    }

    @Test
    @DisplayName("로그인하지 않았을 때 상황")
    void 팔로우_테스트_followerX(){
        //given
        long follower = loginService.getLoginMemberIdx();
        long followee = memberMapper.findById(testFolloweeUser.getEmail()).getUserId();

        //when
        ForbiddenException fe = assertThrows(ForbiddenException.class, () -> followService.subscribe(follower, followee));

        //then
        assertThat(fe.getClass()).isEqualTo(ForbiddenException.class);
    }

    @Test
    void 언팔로우_정상_테스트() {
        //given
        loginService.login(memberDTO.getEmail(), memberDTO.getPassword());
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
        long followee = memberMapper.findById(testFolloweeUser.getEmail()).getUserId();
        long follower = loginService.getLoginMemberIdx();

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
        loginService.login(memberDTO.getEmail(), memberDTO.getPassword());
        long follower = loginService.getLoginMemberIdx();
        long followee = memberMapper.findById(testFolloweeUser.getEmail()).getUserId();

        //when
        NotFollowingUserException ne = assertThrows(NotFollowingUserException.class, () -> followService.unsubscribe(follower, followee));

        //then
        assertThat(ne.getClass()).isEqualTo(NotFollowingUserException.class);
    }

    @Test
    void 팔로우_목록_가져오기(){
        //given
        loginService.login(memberDTO.getEmail(), memberDTO.getPassword());
        long follower = loginService.getLoginMemberIdx();
        long followee = memberMapper.findById(testFolloweeUser.getEmail()).getUserId();
        followService.subscribe(follower, followee);

        //when
        List<Map<String, Long>> followees = followService.getFolloweeByUserId(follower);

        //then
        assertThat(followees.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("유저 아이디가 존재X")
    void 팔로우_목록_가져오기X(){
        //given
        loginService.login(memberDTO.getEmail(), memberDTO.getPassword());
        long userId = 0L;

        //when
        NotExistUserException ne = assertThrows(NotExistUserException.class, () -> followService.getFolloweeByUserId(userId));

        //then
        assertThat(ne.getClass()).isEqualTo(NotExistUserException.class);
    }

    @Test
    @DisplayName("카운트 쿼리 사용")
    void 팔로우_숫자_가져오기() {
        //given
        loginService.login(memberDTO.getEmail(), memberDTO.getPassword());
        long follower = loginService.getLoginMemberIdx();
        long followee = memberMapper.findById(testFolloweeUser.getEmail()).getUserId();
        followService.subscribe(follower, followee);

        //when
        long totalCount = followService.getFolloweeCountByUserId(follower);

        //then
        assertThat(totalCount).isEqualTo(1);
    }
}