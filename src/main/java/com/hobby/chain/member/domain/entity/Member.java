package com.hobby.chain.member.domain.entity;

import com.hobby.chain.member.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.apache.ibatis.annotations.Param;

import java.sql.Date;
import java.sql.Timestamp;

@Getter
@Builder
@AllArgsConstructor
public class Member {
    private long userId;
    private String email;
    private String password;
    private String name;
    private String nickName;
    private String phoneNumber;
    private Gender gender;
    private String birth;
    private Timestamp regdate;

}
