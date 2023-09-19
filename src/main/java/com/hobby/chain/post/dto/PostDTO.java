package com.hobby.chain.post.dto;

import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Builder
public class PostDTO {
    private long userIdx;
    private String content;
    private List<ImageDTO> images;
    private Timestamp regdate;
}
