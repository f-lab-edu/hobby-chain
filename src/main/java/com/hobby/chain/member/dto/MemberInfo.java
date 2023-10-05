package com.hobby.chain.member.dto;

import com.hobby.chain.member.Gender;
import lombok.Getter;

import java.sql.Date;

@Getter
public class MemberInfo {
    private String email;
    private String name;
    private String nickName;
    private String phoneNumber;
    private Gender gender;
    private String birth;
    private Date regdate;
}
