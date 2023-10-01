package com.hobby.chain.post.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ImageDTO {
    private long postId;
    private String imageId;
    private String imageName;
    private String imagePath;
    private long fileSize;
}
