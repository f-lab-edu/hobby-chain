package com.hobby.chain.push;

import lombok.Getter;

@Getter
public enum PushType {
    NEW_POST("NEW POST");

    private String type;

    PushType(String type) {
        this.type = type;
    }
}
