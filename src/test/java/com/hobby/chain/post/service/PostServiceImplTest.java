package com.hobby.chain.post.service;

import com.hobby.chain.member.Gender;
import com.hobby.chain.member.domain.mapper.MemberMapper;
import com.hobby.chain.member.dto.MemberDTO;
import com.hobby.chain.member.service.MemberLoginService;
import com.hobby.chain.member.service.MemberService;
import com.hobby.chain.post.domain.mapper.PostMapper;
import com.hobby.chain.post.dto.PostDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@Transactional
class PostServiceImplTest {
    private PostMapper postMapper;
    private MemberMapper memberMapper;

    private PostService service;
    private MemberService memberService;
    private MemberLoginService loginService;

    @Autowired
    public PostServiceImplTest(PostMapper postMapper, MemberMapper memberMapper, PostService service, MemberService memberService, MemberLoginService loginService) {
        this.postMapper = postMapper;
        this.memberMapper = memberMapper;
        this.service = service;
        this.memberService = memberService;
        this.loginService = loginService;
    }

    private MemberDTO memberDTO;
    @BeforeEach
    void testMemberSetUp(){
        memberDTO = MemberDTO.builder()
                .email("qpqp7376@gmail.com")
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

    @Test
    void 게시물_업로드_이미지X(){
        //given
        PostDTO postDTO = PostDTO.builder()
                .userIdx(loginService.getLoginMemberIdx())
                .post_content("테스트").build();

        //when
        service.uploadNewPost(postDTO.getUserIdx(), postDTO);

        //then
        assertThat(postMapper.getAllPostWithoutImage()).isNotNull();
    }
}