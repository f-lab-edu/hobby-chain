package com.hobby.chain.post.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImageDTO {
    private long postId;
    private String imageId;
    private String imageName;
    private String imagePath;
    private long fileSize;
}
