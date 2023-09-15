package com.hobby.chain.member.dto;

import com.hobby.chain.member.Regex;
import org.junit.jupiter.api.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

class MemberDTOTest {

    @Test
    void 아이디_정규식_테스트(){
        //given
        String memberId = "qpqp7371@gmail.com";

        //when
        boolean result = regexMatching(memberId, Regex.REGEX_USER_ID);

        //then
        assertTrue(result);
    }

    @Test
    void 아이디_정규식_테스트_예외(){
        //given
        String memberId = "qpqp7371@gmail";

        //when
        boolean result = regexMatching(memberId, Regex.REGEX_USER_ID);

        //then
        assertFalse(result);
    }

    @Test
    void 비밀번호_정규식_테스트(){
        //given
        String memberPw = "xsd12*";

        //when
        boolean result = regexMatching(memberPw, Regex.REGEX_PASSWORD);

        //then
        assertTrue(result);
    }

    @Test
    void 비밀번호_정규식_테스트_예외(){
        //given
        String memberPw = "xsd12";

        //when
        boolean result = regexMatching(memberPw, Regex.REGEX_PASSWORD);

        //then
        assertFalse(result);
    }

    @Test
    void 이름_정규식_테스트(){
        //given
        String memberName = "정서현";

        //when
        boolean result = regexMatching(memberName, Regex.REGEX_NAME);

        //then
        assertTrue(result);
    }

    @Test
    void 이름_정규식_테스트_예외(){
        //given
        String memberName = "tjgus";

        //when
        boolean result = regexMatching(memberName, Regex.REGEX_NAME);

        //then
        assertFalse(result);
    }

    @Test
    void 닉네임_정규식_테스트(){
        //given
        String memberNickName = "서현";

        //when
        boolean result = regexMatching(memberNickName, Regex.REGEX_NICKNAME);

        //then
        assertTrue(result);
    }

    @Test
    void 닉네임_정규식_테스트_예외(){
        //given
        String memberNickName = "slrspdla";

        //when
        boolean result = regexMatching(memberNickName, Regex.REGEX_NICKNAME);

        //then
        assertFalse(result);
    }

    @Test
    void 생일_정규식_테스트(){
        //given
        String memberBirth = "2004-02-27";

        //when
        boolean result = regexMatching(memberBirth, Regex.REGEX_BIRTH);

        //then
        assertTrue(result);
    }

    @Test
    void 생일_정규식_테스트_예외(){
        //given
        String memberBirth = "040227";

        //when
        boolean result = regexMatching(memberBirth, Regex.REGEX_BIRTH);

        //then
        assertFalse(result);
    }

    @Test
    void 번호_정규식_테스트(){
        //given
        String memberPhone = "010-4600-4123";

        //when
        boolean result = regexMatching(memberPhone, Regex.REGEX_PHONENUMBER);

        //then
        assertTrue(result);
    }

    @Test
    void 번호_정규식_테스트_예외(){
        //given
        String memberPhone = "1234567891";

        //when
        boolean result = regexMatching(memberPhone, Regex.REGEX_PHONENUMBER);

        //then
        assertFalse(result);
    }

    private boolean regexMatching(String testStr, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(testStr);

        return matcher.find();
    }
}