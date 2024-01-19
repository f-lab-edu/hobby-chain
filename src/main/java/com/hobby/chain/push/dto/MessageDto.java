package com.hobby.chain.push.dto;

import com.hobby.chain.push.PushType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
@AllArgsConstructor
public class MessageDto {
    private String title;
    private String body;
    private String createTime;
    private String token;

    //JSON converting 사용을 위한 protected 기본 생성자
    protected MessageDto(){
    }
}
