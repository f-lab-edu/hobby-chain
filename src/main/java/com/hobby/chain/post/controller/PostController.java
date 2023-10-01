package com.hobby.chain.post.controller;

import com.hobby.chain.member.service.MemberLoginService;
import com.hobby.chain.post.dto.PostDTO;
import com.hobby.chain.post.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;
    private final MemberLoginService loginService;

    public PostController(PostService service, MemberLoginService loginService) {
        this.postService = service;
        this.loginService = loginService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void uploadNewPost(@RequestParam String content,
                              @RequestParam List<MultipartFile> images){
        postService.uploadNewPost(loginService.getLoginMemberIdx(), content, images);
    }
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<PostDTO> getPosts(){
        return postService.getPosts();
    }

    @GetMapping("/one")
    @ResponseStatus(HttpStatus.OK)
    public PostDTO getPost(){
        return postService.getPost(loginService.getLoginMemberIdx());
    }

}
