package com.hobby.chain.member.service;

import com.hobby.chain.member.domain.entity.Member;
import com.hobby.chain.member.dto.MemberDTO;
import org.springframework.validation.Errors;

import java.util.Map;

public interface MemberService {
    public void signUp(MemberDTO memberDTO);
    public int exist(String userId);
}
