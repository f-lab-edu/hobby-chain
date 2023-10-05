package com.hobby.chain.member.service;

import com.hobby.chain.member.domain.entity.Member;
import com.hobby.chain.member.dto.MemberDTO;
import com.hobby.chain.member.dto.MemberInfo;
import org.springframework.validation.Errors;

import javax.servlet.http.HttpSession;
import java.util.Map;

public interface MemberService {
    public void signUp(MemberDTO memberDTO);
    public boolean exist(String email);
    public MemberInfo getMemberInfo();
}
