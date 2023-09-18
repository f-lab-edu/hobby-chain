package com.hobby.chain.member.service;

import com.hobby.chain.member.Gender;
import com.hobby.chain.member.dto.MemberDTO;
import com.hobby.chain.member.dto.MemberLogin;
import com.hobby.chain.member.exception.DuplicationException;
import com.hobby.chain.member.exception.IncorrectPasswordException;
import com.hobby.chain.member.exception.NotExistUserException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class MemberServiceImplTest {

    private final MemberService memberService;
    private PasswordEncoder passwordEncoder;
    private final MemberLoginService loginService;
    @Mock
    private MemberLogin memberLogin;

    @Autowired
    public MemberServiceImplTest(MemberService memberService, PasswordEncoder passwordEncoder, MemberLoginService loginService) {
        this.memberService = memberService;
        this.passwordEncoder = passwordEncoder;
        this.loginService = loginService;
    }

    @Test
    @DisplayName("일반적인 회원 가입 성공")
    void 회원가입_성공(){
        //given
        MemberDTO memberDTO = MemberDTO.builder()
                .userId("qpqp7375@gmail.com")
                .password("xxxx")
                .name("정서현")
                .nickName("서현")
                .phoneNumber("010-4600-4123")
                .gender(Gender.M)
                .birth("20040227").build();

        //when
        memberService.signUp(memberDTO);

        //then
        assertTrue(memberService.exist(memberDTO.getUserId()));
    }

    @Test
    @DisplayName("중복 회원 가입 테스트")
    void 중복_회원_가입(){
        //given
        MemberDTO memberDTO1 = MemberDTO.builder()
                .userId("qpqp7375@gmail.com")
                .password("xxxx")
                .name("정서현")
                .nickName("서현")
                .phoneNumber("010-4600-4123")
                .gender(Gender.M)
                .birth("20040227").build();
        memberService.signUp(memberDTO1);

        MemberDTO memberDTO2 = MemberDTO.builder()
                .userId("qpqp7375@gmail.com")
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
    void 로그인_성공_테스트(){
        //given
        String userId = "qpqp7374@gmail.com";
        String pwd = "xxxx";

        //when
        loginService.login(userId, pwd);

        //then
        assertThat(memberLogin).isNotNull();
    }

    @Test
    void 로그인_실패_아이디X(){
        //given
        String userId = "r2gards1@gmail.com";
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
        String userId = "qpqp7374@gmail.com";
        String pwd = "xdre12";

        //when
        IncorrectPasswordException ie = assertThrows(IncorrectPasswordException.class,
                () -> loginService.login(userId, pwd));

        //then
        assertThat(ie.getClass()).isEqualTo(IncorrectPasswordException.class);
    }

    @Test
    void 아이디_얻기_로그인X(){
        //when
        NullPointerException ne = assertThrows(NullPointerException.class, () -> loginService.getLoginMemberId());

        //then
        assertThat(ne.getClass()).isEqualTo(NullPointerException.class);
    }

}