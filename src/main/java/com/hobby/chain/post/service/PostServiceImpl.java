package com.hobby.chain.post.service;

import com.hobby.chain.post.domain.mapper.PostMapper;
import com.hobby.chain.post.dto.ImageDTO;
import com.hobby.chain.post.dto.PostDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements PostService{
    private final PostMapper mapper;

    public PostServiceImpl(PostMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public void uploadNewPost(long UserIdx, PostDTO postDTO) {
        mapper.insertPost(PostDTO.builder()
                .userIdx(UserIdx)
                .content(postDTO.getContent()).build());

        if(postDTO.getImages() != null){
            List<ImageDTO> images = postDTO.getImages();
            for(ImageDTO image : images){
                //아직 미구현
            }
        }
    }

    @Override
    public PostDTO getAllPost() {
        return mapper.getAllPost();
    }

    @Override
    public PostDTO getPost(long postId) {
        boolean existsImage = mapper.isExistsImage(postId);

        if(existsImage){
            return mapper.getPostWithImage(postId);
        } else {
            return mapper.getPostWithoutImage(postId);
        }
    }
}
