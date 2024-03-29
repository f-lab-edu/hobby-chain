package com.hobby.chain.like.service;

import com.hobby.chain.like.exception.AlreadyLikeException;
import com.hobby.chain.member.Gender;
import com.hobby.chain.member.domain.mapper.MemberMapper;
import com.hobby.chain.member.dto.MemberDTO;
import com.hobby.chain.member.exception.ForbiddenException;
import com.hobby.chain.member.service.MemberLoginService;
import com.hobby.chain.member.service.MemberService;
import com.hobby.chain.post.domain.mapper.PostMapper;
import com.hobby.chain.post.exception.NoExistsPost;
import com.hobby.chain.post.service.PostService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class LikeServiceImplTest {
    private final LikeService likeService;
    private final MemberService memberService;
    private final MemberLoginService loginService;
    private final PostService postService;
    private final PostMapper postMapper;
    private final MemberMapper memberMapper;

    private MemberDTO memberDTO;

    @Autowired
    LikeServiceImplTest(LikeService likeService, MemberService memberService, MemberLoginService loginService, PostService postService, PostMapper postMapper, MemberMapper memberMapper) {
        this.likeService = likeService;
        this.memberService = memberService;
        this.loginService = loginService;
        this.postService = postService;
        this.postMapper = postMapper;
        this.memberMapper = memberMapper;
    }

    @BeforeEach
    void setUp(){
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

        loginService.login(memberDTO.getEmail(), memberDTO.getPassword());
    }

    @AfterEach
    void deleteAllData(){
        memberMapper.deleteAll();
    }

    @Test
    void 좋아요_정상_테스트() {
        //given
        postService.uploadNewPost(loginService.getLoginMemberIdx(), "test", null);
        long postId = postMapper.getLatestId();
        long userId = loginService.getLoginMemberIdx();

        //when
        likeService.like(userId, postId);

        //then
        assertThat(likeService.getLikeCount(postId)).isEqualTo(1);
    }

    @Test
    @DisplayName("이미 좋아요하고 있는 경우")
    void 좋아요_실패_테스트() {
        //given
        postService.uploadNewPost(loginService.getLoginMemberIdx(), "test", null);
        long postId = postMapper.getLatestId();
        long userId = loginService.getLoginMemberIdx();

        //when
        likeService.like(userId, postId);
        AlreadyLikeException ae = assertThrows(AlreadyLikeException.class, () -> likeService.like(userId, postId));

        //then
        assertThat(ae.getClass()).isEqualTo(AlreadyLikeException.class);
    }

    @Test
    @DisplayName("게시물이 없는 경우")
    void 좋아요_실패_테스트2() {
        //given
        long postId = 0L;
        long userId = loginService.getLoginMemberIdx();

        //when
        NoExistsPost ne = assertThrows(NoExistsPost.class, () -> likeService.like(userId, postId));

        //then
        assertThat(ne.getClass()).isEqualTo(NoExistsPost.class);
    }

    @Test
    void 좋아요_취소_정상_테스트() {
        //given
        postService.uploadNewPost(loginService.getLoginMemberIdx(), "test", null);
        long postId = postMapper.getLatestId();
        long userId = loginService.getLoginMemberIdx();
        likeService.like(userId, postId);

        //when
        likeService.unlike(userId, postId);

        //then
        assertThat(likeService.getLikeCount(postId)).isEqualTo(0);
    }

    @Test
    @DisplayName("게시물이 없는 경우")
    void 좋아요_취소_실패_테스트() {
        //given
        long postId = 0L;
        long userId = loginService.getLoginMemberIdx();

        //when
        NoExistsPost ne = assertThrows(NoExistsPost.class, () -> likeService.unlike(userId, postId));

        //then
        assertThat(ne.getClass()).isEqualTo(NoExistsPost.class);
    }

    @Test
    @DisplayName("좋아요하지 않은 경우")
    void 좋아요_취소_실패_테스트2() {
        //given
        long postId = postMapper.getLatestId();
        long userId = loginService.getLoginMemberIdx();

        //when
        ForbiddenException fe = assertThrows(ForbiddenException.class, () -> likeService.unlike(userId, postId));

        //then
        assertThat(fe.getClass()).isEqualTo(ForbiddenException.class);
    }
}