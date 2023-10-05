package com.hobby.chain.member.service;

import com.hobby.chain.member.Gender;
import com.hobby.chain.member.dto.MemberDTO;
import com.hobby.chain.member.exception.DuplicationException;
import com.hobby.chain.member.exception.ForbiddenException;
import com.hobby.chain.member.exception.IncorrectPasswordException;
import com.hobby.chain.member.exception.NotExistUserException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class MemberServiceImplTest {

    private final MemberService memberService;
    private final MemberLoginService loginService;

    @Autowired
    public MemberServiceImplTest(MemberService memberService, MemberLoginService loginService) {
        this.memberService = memberService;
        this.loginService = loginService;
    }

    @Test
    @DisplayName("일반적인 회원 가입 성공")
    void 회원가입_성공(){
        //given
        MemberDTO memberDTO = MemberDTO.builder()
                .email("qpqp7377@gmail.com")
                .password("xxxx")
                .name("정서현")
                .nickName("서현")
                .phoneNumber("010-4600-4123")
                .gender(Gender.M)
                .birth("20040227").build();

        //when
        memberService.signUp(memberDTO);

        //then
        assertTrue(memberService.exist(memberDTO.getEmail()));
    }

    @Test
    @DisplayName("중복 회원 가입 테스트")
    void 중복_회원_가입(){
        //given
        MemberDTO memberDTO1 = MemberDTO.builder()
                .email("qpqp7375@gmail.com")
                .password("xxxx")
                .name("정서현")
                .nickName("서현")
                .phoneNumber("010-4600-4123")
                .gender(Gender.M)
                .birth("20040227").build();
        memberService.signUp(memberDTO1);

        MemberDTO memberDTO2 = MemberDTO.builder()
                .email("qpqp7375@gmail.com")
                .password("xxxx")
                .name("정서현")
                .nickName("서현")
                .phoneNumber("010-4600-4123")
                .gender(Gender.M)
                .birth("20040227").build();

        //when
        DuplicationException de = Assertions.assertThrows(DuplicationException.class, () -> memberService.signUp(memberDTO2));

        //then
        assertThat(de.getClass()).isEqualTo(DuplicationException.class);
    }



    @Test
    void 회원_정보_조회(){
        //given
        MemberDTO memberDTO = MemberDTO.builder()
                .email("qpqp7371@gmail.com")
                .password("xxxx")
                .name("정서현")
                .nickName("서현")
                .phoneNumber("010-4600-4123")
                .gender(Gender.M)
                .birth("20040227").build();
        memberService.signUp(memberDTO);

        //when


        //then
    }

}