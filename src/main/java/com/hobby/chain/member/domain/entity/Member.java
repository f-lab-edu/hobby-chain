package com.hobby.chain.member.domain.entity;

import lombok.Builder;
import lombok.Getter;
import org.apache.ibatis.annotations.Param;

import java.sql.Date;
import java.sql.Timestamp;

@Getter
public class Member {
    private long idx;
    private String userId; //이메일
    private String password;
    private String name;
    private String nickName;
    private String phoneNumber;
    private String gender;
    private Date birth;
    private Timestamp regdate;


    @Builder
    public Member(
            @Param("userId") String userId,
            @Param("password") String password,
            @Param("name") String name,
            @Param("nickName") String nickName,
            @Param("phoneNumber") String phoneNumber,
            @Param("gender") String gender,
            @Param("birth") Date birth,
            @Param("regdate") Timestamp regdate){
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.nickName = nickName;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.birth = birth;
        this.regdate = regdate;
    }

}
