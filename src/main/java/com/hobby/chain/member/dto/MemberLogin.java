package com.hobby.chain.member.dto;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
@Builder
public class MemberLogin {
    @NotBlank(message = "ID를 입력해 주세요.")
    private String userId;

    @NotBlank(message = "비밀번호를 입력해 주세요.")
    private String password;

    public MemberLogin(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }
}
