package com.hobby.chain.post.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class PostImage {
    private long imageId;
    private long postId;
    private String imagePath;
    private long fileSize;

}
