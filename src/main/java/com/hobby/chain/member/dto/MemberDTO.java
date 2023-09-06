package com.hobby.chain.member.dto;

import lombok.Getter;

import java.sql.Date;

@Getter
public class MemberDTO {
    private String userId;
    private String password;
    private String name;
    private String nickName;
    private String phoneNumber;
    private String gender;
    private Date birth;

    public MemberDTO(String userId,
                     String password,
                     String name,
                     String nickName,
                     String phoneNumber,
                     String gender,
                     Date birth) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.nickName = nickName;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.birth = birth;
    }
}
