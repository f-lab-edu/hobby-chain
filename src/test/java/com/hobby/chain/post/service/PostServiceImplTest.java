package com.hobby.chain.post.service;

import com.hobby.chain.member.Gender;
import com.hobby.chain.member.domain.mapper.MemberMapper;
import com.hobby.chain.member.dto.MemberDTO;
import com.hobby.chain.member.service.MemberLoginService;
import com.hobby.chain.member.service.MemberService;
import com.hobby.chain.post.domain.mapper.PostMapper;
import com.hobby.chain.post.dto.PostDTO;
import com.hobby.chain.post.dto.ResponsePost;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

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
    private final MemberLoginService loginService;

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
    void 게시물_업로드_이미지X() {
        //given
        PostDTO postDTO = PostDTO.builder()
                .userIdx(loginService.getLoginMemberIdx())
                .postContent("테스트").build();

        //when
        service.uploadNewPost(loginService.getLoginMemberIdx(), "테스트", null);

        //then
        assertThat(postMapper.getAllPost()).isNotNull();
    }

    @Test
    void 게시물_업로드_이미지O() throws Exception {
        //given
        PostDTO postDTO = PostDTO.builder()
                .userIdx(loginService.getLoginMemberIdx())
                .postContent("테스트").build();

        MultipartFile file = new MockMultipartFile("image", "test.png", "image/png",
                new FileInputStream("/Users/mac/Downloads/test.png"));
        List<MultipartFile> files = new ArrayList<>();
        files.add(file);

        //when
        service.uploadNewPost(loginService.getLoginMemberIdx(), "테스트", files);

        //then
        assertThat(postMapper.getAllPost()).isNotNull();
    }

    @Test
    void 게시물_조회(){
        //given
        PostDTO postDTO = PostDTO.builder()
                .userIdx(loginService.getLoginMemberIdx())
                .postContent("테스트").build();
        for (int i = 0; i < 20; i++){
            service.uploadNewPost(loginService.getLoginMemberIdx(), "테스트", null);
        }

        //when
        List<ResponsePost> posts = service.getPosts(0);

        //then
        assertThat(posts.size()).isEqualTo(15);
    }
}