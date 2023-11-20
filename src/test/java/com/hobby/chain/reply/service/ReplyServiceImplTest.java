package com.hobby.chain.reply.service;

import com.hobby.chain.member.Gender;
import com.hobby.chain.member.dto.MemberDTO;
import com.hobby.chain.member.service.MemberLoginService;
import com.hobby.chain.member.service.MemberService;
import com.hobby.chain.post.domain.mapper.PostMapper;
import com.hobby.chain.post.service.PostService;
import com.hobby.chain.reply.dto.ReplyResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
class ReplyServiceImplTest {
    private final ReplyService replyService;
    private final PostService postService;
    private final PostMapper postMapper;
    private final MemberService memberService;
    private final MemberLoginService loginService;

    @Autowired
    ReplyServiceImplTest(ReplyService replyService, PostService postService, PostMapper postMapper, MemberService memberService, MemberLoginService loginService) {
        this.replyService = replyService;
        this.postService = postService;
        this.postMapper = postMapper;
        this.memberService = memberService;
        this.loginService = loginService;
    }

    @BeforeEach
    void setUp(){
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

        postService.uploadNewPost(loginService.getLoginMemberIdx(), "test", null);
    }

    @Test
    void 댓글추가_정상_테스트() {
        //given
        long userId = loginService.getLoginMemberIdx();
        long postId = postMapper.getLatestId();

        //when
        replyService.writeReply(postId, userId, "testReply");

        //then
        Assertions.assertThat(replyService.getReplysByPostId(postId, 0).size()).isEqualTo(1);
    }

    @Test
    void 댓글조회_정상_테스트() {
        //given
        long userId = loginService.getLoginMemberIdx();
        long postId = postMapper.getLatestId();
        replyService.writeReply(postId, userId, "testReply");

        //when
        List<ReplyResponse> replys = replyService.getReplysByPostId(postId, 0);

        //then
        Assertions.assertThat(replys.size()).isEqualTo(1);
    }
}