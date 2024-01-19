package com.hobby.chain.like.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class Like {
    private long postId;
    private long userId;
}
