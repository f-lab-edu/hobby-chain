package com.hobby.chain.member.domain.entity;

import com.hobby.chain.member.Gender;
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
    private Gender gender;
    private String birth;
    private Timestamp regdate;


    @Builder
    public Member(
            String userId,
            String password,
            String name,
            String nickName,
            String phoneNumber,
            Gender gender,
            String birth,
            Timestamp regdate){
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
