package com.hobby.chain.post.controller;

import com.hobby.chain.member.service.MemberLoginService;
import com.hobby.chain.post.dto.PostDTO;
import com.hobby.chain.post.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/posts")
public class PostController {
    private final PostService service;
    private final MemberLoginService loginService;

    public PostController(PostService service, MemberLoginService loginService) {
        this.service = service;
        this.loginService = loginService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void uploadNewPost(@RequestBody PostDTO postDTO){
        service.uploadNewPost(loginService.getLoginMemberIdx(), postDTO);
    }
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public PostDTO getAllPost(){
        return service.getAllPost();
    }

    @GetMapping("/one")
    @ResponseStatus(HttpStatus.OK)
    public PostDTO getPost(){
        return service.getPost(loginService.getLoginMemberIdx());
    }
}
