package com.hobby.chain.post.controller;

import com.hobby.chain.member.service.MemberLoginService;
import com.hobby.chain.post.dto.PostDTO;
import com.hobby.chain.post.dto.ResponsePost;
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

    public PostController(PostService service) {
        this.postService = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void uploadNewPost(@RequestParam String content,
                              @RequestParam List<MultipartFile> images, Long userId){
        postService.uploadNewPost(userId, content, images);
    }
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ResponsePost> getPosts(@RequestParam(defaultValue = "0") long currentSeq){
        return postService.getPosts(currentSeq);
    }

    @GetMapping("/{postId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponsePost getPost(@PathVariable long postId){
        return postService.getPost(postId);
    }

    @PutMapping("/{postId}")
    public void updatePost(@PathVariable long postId, @RequestParam String content, Long userId){
        postService.updatePost(userId, postId, content);
    }

    @DeleteMapping("/{postId}")
    public void deletePost(@PathVariable long postId, Long userId){
        postService.deletePost(userId, postId);
    }

}
