package com.hobby.chain.post.service;

import com.hobby.chain.member.Gender;
import com.hobby.chain.member.domain.mapper.MemberMapper;
import com.hobby.chain.member.dto.MemberDTO;
import com.hobby.chain.member.exception.ForbiddenException;
import com.hobby.chain.member.service.MemberLoginService;
import com.hobby.chain.member.service.MemberService;
import com.hobby.chain.post.domain.mapper.PostMapper;
import com.hobby.chain.post.dto.PostDTO;
import com.hobby.chain.post.dto.ResponsePost;
import org.junit.jupiter.api.AfterEach;
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

@SpringBootTest
@Transactional
class PostServiceImplTest {
    private PostMapper postMapper;

    private PostService service;
    private MemberService memberService;
    private final MemberLoginService loginService;
    private final MemberMapper memberMapper;

    @Autowired
    public PostServiceImplTest(PostMapper postMapper, PostService service, MemberService memberService, MemberLoginService loginService, MemberMapper memberMapper) {
        this.postMapper = postMapper;
        this.service = service;
        this.memberService = memberService;
        this.loginService = loginService;
        this.memberMapper = memberMapper;
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

    @AfterEach
    void deleteAllData(){
        memberMapper.deleteAll();
    }

    @Test
    void 게시물_업로드_이미지X() {
        //given
        PostDTO postDTO = PostDTO.builder()
                .userId(loginService.getLoginMemberIdx())
                .postContent("테스트").build();

        //when
        service.uploadNewPost(loginService.getLoginMemberIdx(), postDTO.getPostContent(), null);
        long postId = postMapper.getLatestId();

        //then
        assertThat(postMapper.getPost(postId)).isNotNull();
    }

    @Test
    void 게시물_업로드_이미지O() throws Exception {
        //given
        PostDTO postDTO = PostDTO.builder()
                .userId(loginService.getLoginMemberIdx())
                .postContent("테스트").build();

        MultipartFile file = new MockMultipartFile("image", "test.png", "image/png",
                new FileInputStream("/Users/mac/Downloads/test.png"));
        List<MultipartFile> files = new ArrayList<>();
        files.add(file);

        //when
        service.uploadNewPost(loginService.getLoginMemberIdx(), "테스트", files);
        long postId = postMapper.getLatestId();

        //then
        assertThat(postMapper.getPost(postId)).isNotNull();
    }

    @Test
    void 게시물_조회(){
        //given
        for (int i = 0; i < 20; i++){
            service.uploadNewPost(loginService.getLoginMemberIdx(), "테스트", null);
        }

        //when
        List<ResponsePost> posts = service.getPosts(0);

        //then
        assertThat(posts.size()).isEqualTo(15);
    }

    @Test
    void 게시물_수정_권한O(){
        //given
        service.uploadNewPost(loginService.getLoginMemberIdx(), "테스트", null);

        //when
        service.updatePost(loginService.getLoginMemberIdx(), postMapper.getLatestId(), "test");
        ResponsePost post = service.getPost(postMapper.getLatestId());

        //then
        assertThat(post.getPostContent()).isEqualTo("test");
    }

    @Test
    void 게시물_수정_권한X(){
        //given
        service.uploadNewPost(loginService.getLoginMemberIdx(), "테스트", null);

        //when
        ForbiddenException fe = assertThrows(ForbiddenException.class,
                () -> service.updatePost(0, postMapper.getLatestId(), "test"));

        //then
        assertThat(fe.getClass()).isEqualTo(ForbiddenException.class);
    }

    @Test
    void 게시물_삭제_권한O(){
        //given
        service.uploadNewPost(loginService.getLoginMemberIdx(), "테스트", null);

        //when
        service.deletePost(loginService.getLoginMemberIdx(), postMapper.getLatestId());

        //then
        assertThat(postMapper.getTotalCount()).isEqualTo(0);
    }

    @Test
    void 게시물_삭제_권한X(){
        //given
        service.uploadNewPost(loginService.getLoginMemberIdx(), "테스트", null);

        //when
        ForbiddenException fe = assertThrows(ForbiddenException.class,
                () -> service.deletePost(0, postMapper.getLatestId()));

        //then
        assertThat(fe.getClass()).isEqualTo(ForbiddenException.class);
    }
}