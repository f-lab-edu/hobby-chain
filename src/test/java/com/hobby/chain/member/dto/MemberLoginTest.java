package com.hobby.chain.member.dto;

import com.hobby.chain.member.Regex;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

import static org.assertj.core.api.Assertions.*;

class MemberLoginTest {
    private Validator validator;

    @BeforeEach
    public void setValidator() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    void 정상_테스트(){
        //given
        MemberLogin memberLogin = MemberLogin.builder()
                .userId("qpqp7371@gmail.com")
                .password("xsdf12*").build();

        //when
        Set<ConstraintViolation<MemberLogin>> validate = validator.validate(memberLogin);

        //then
        assertThat(validate.size()).isEqualTo(0);
    }
    @Test
    void 빈칸_테스트(){
        //given
        MemberLogin memberLogin = MemberLogin.builder()
                .userId("")
                .password(" ").build();

        //when
        Set<ConstraintViolation<MemberLogin>> validate = validator.validate(memberLogin);

        //then
        assertThat(validate.size()).isEqualTo(2);
    }

}