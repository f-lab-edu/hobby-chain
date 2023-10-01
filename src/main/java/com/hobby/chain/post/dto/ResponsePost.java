package com.hobby.chain.post.dto;

import lombok.*;

import java.sql.Timestamp;
import java.util.List;

@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponsePost {
    private long postId;
    private long userIdx;
    private String postContent;
    private List<ImageDTO> images;
    private Timestamp regdate;
}
