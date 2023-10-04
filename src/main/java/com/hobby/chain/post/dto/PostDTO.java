package com.hobby.chain.post.dto;

import lombok.*;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PostDTO {
    private long postId;
    private long userId;
    private String postContent;
    private Timestamp regdate;
    private List<ImageDTO> images;
}
