package com.hobby.chain.member.dto;

import com.hobby.chain.member.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Timestamp;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberInfo {
    private long userId;
    private String email;
    private String name;
    private String nickName;
    private String phoneNumber;
    private Gender gender;
    private Date birth;
    private Timestamp regdate;
}
