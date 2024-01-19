package com.hobby.chain.member.dto;

import com.hobby.chain.member.Gender;
import com.hobby.chain.member.Regex;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Builder
public class MemberDTO {
    @Pattern(regexp = Regex.REGEX_USER_ID, message = "이메일 형식이 올바르지 않습니다.")
    private String email;

    @Pattern(regexp = Regex.REGEX_PASSWORD, message = "영문, 숫자, 특수문자를 사용하여 6~15자리를 입력해 주세요.")
    private String password;

    @Pattern(regexp = Regex.REGEX_NAME, message = "이름의 형식이 올바르지 않습니다.")
    private String name;

    @Pattern(regexp = Regex.REGEX_NICKNAME, message = "닉네임 형식이 올바르지 않습니다.")
    private String nickName;

    @Pattern(regexp = Regex.REGEX_PHONENUMBER, message = "번호 형식이 올바르지 않습니다.")
    private String phoneNumber;

    @NotBlank(message = "필수 입력 사항입니다.")
    private Gender gender;

    @Pattern(regexp = Regex.REGEX_BIRTH, message = "생일 형식이 올바르지 않습니다.")
    private String birth;

    public MemberDTO(String email,
                     String password,
                     String name,
                     String nickName,
                     String phoneNumber,
                     Gender gender,
                     String birth) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.nickName = nickName;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.birth = birth;
    }
}
