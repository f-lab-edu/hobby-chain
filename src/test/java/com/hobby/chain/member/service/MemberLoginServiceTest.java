package com.hobby.chain.member.service;

import com.hobby.chain.member.Gender;
import com.hobby.chain.member.domain.mapper.MemberMapper;
import com.hobby.chain.member.dto.MemberDTO;
import com.hobby.chain.member.exception.ForbiddenException;
import com.hobby.chain.member.exception.IncorrectPasswordException;
import com.hobby.chain.member.exception.NotExistUserException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Transactional
@SpringBootTest
public class MemberLoginServiceTest {
    private final MemberService memberService;
    private final MemberLoginService loginService;
    private final MemberMapper memberMapper;

    @Autowired
    public MemberLoginServiceTest(MemberService memberService, MemberLoginService loginService, MemberMapper memberMapper) {
        this.memberService = memberService;
        this.loginService = loginService;
        this.memberMapper = memberMapper;
    }

    @BeforeEach
    void setUp_회원가입(){
        MemberDTO memberDTO = MemberDTO.builder()
                .email("qpqp7371@gmail.com")
                .password("xxxx")
                .name("정서현")
                .nickName("서현")
                .phoneNumber("010-4600-4123")
                .gender(Gender.M)
                .birth("20040227").build();
        memberService.signUp(memberDTO);
    }

    @AfterEach
    void deleteAllData(){
        memberMapper.deleteAll();
    }

    @Test
    void 로그인_성공_테스트(){
        //given
        String userId = "qpqp7371@gmail.com";
        String pwd = "xxxx";

        //when
        loginService.login(userId, pwd);

        //then
        assertThat(userId).isEqualTo(memberService.getMemberInfo(loginService.getLoginMemberIdx()).getEmail());
    }

    @Test
    void 로그인_실패_아이디X(){
        //given
        String userId = "r2gards2@gmail.com";
        String pwd = "xxxx";

        //when
        NotExistUserException ne = assertThrows(NotExistUserException.class,
                () -> loginService.login(userId, pwd));

        //then
        assertThat(ne.getClass()).isEqualTo(NotExistUserException.class);
    }

    @Test
    void 로그인_실패_비밀번호_일치X(){
        //given
        String userId = "qpqp7371@gmail.com";
        String pwd = "xdre12";

        //when
        IncorrectPasswordException ie = assertThrows(IncorrectPasswordException.class,
                () -> loginService.login(userId, pwd));

        //then
        assertThat(ie.getClass()).isEqualTo(IncorrectPasswordException.class);
    }

    @Test
    void 아이디_얻기_로그인X() {
        //when
        ForbiddenException fe = assertThrows(ForbiddenException.class, () -> loginService.getLoginMemberIdx());

        //then
        assertThat(fe.getClass()).isEqualTo(ForbiddenException.class);
    }
}
