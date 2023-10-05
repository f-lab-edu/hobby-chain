package com.hobby.chain.member.dto;

import com.hobby.chain.member.Gender;
import lombok.Builder;
import lombok.Getter;

import java.sql.Date;

@Getter
@Builder
public class UpdateRequestInfo {
    private String nickName;
    private String phoneNumber;
    private Gender gender;
    private Date birth;
}
