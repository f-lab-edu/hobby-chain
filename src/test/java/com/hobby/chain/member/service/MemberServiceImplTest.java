package com.hobby.chain.member.service;

import com.hobby.chain.member.dto.MemberDTO;
import com.hobby.chain.member.exception.DuplicationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class MemberServiceImplTest {
    @Autowired
    private MemberServiceImpl memberService;
    private MemberDTO memberDTO;

    @BeforeEach
    void 회원Dto세팅(){
        memberDTO = new MemberDTO("qpqp7371@gmail.com", "xxxx", "정서현", "서현", "010-4600-4123", "M", "20040227");
    }

    @Test
    @DisplayName("일반적인 회원 가입 성공")
    void 회원가입_성공(){
        //given - memberDTO

        //when
        memberService.signUp(memberDTO);
        int isYn = memberService.findById(memberDTO.getUserId());

        //then
        assertThat(isYn).isEqualTo(memberService.findById(memberDTO.getUserId()));
    }

    @Test
    @DisplayName("중복 회원 가입 테스트")
    void 중복_회원_가입(){
        //given - memberDTO

        //when
        DuplicationException de = Assertions.assertThrows(DuplicationException.class, () -> memberService.signUp(memberDTO));

        //then
        assertThat(de.getClass()).isEqualTo(DuplicationException.class);
    }

}