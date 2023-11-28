package com.hobby.chain.member.service;

import com.hobby.chain.member.domain.entity.Member;
import com.hobby.chain.member.dto.MemberDTO;
import com.hobby.chain.member.dto.MemberInfo;
import com.hobby.chain.member.dto.UpdateRequestInfo;
import org.springframework.validation.Errors;

import javax.servlet.http.HttpSession;
import java.util.Map;

public interface MemberService {
    public void signUp(MemberDTO memberDTO);
    public boolean exist(String email);
    public boolean isExistUser(long userId);
    public MemberInfo getMemberInfo(Long userId);
    void updateMemberInfo(Long userId, UpdateRequestInfo requestInfo);
    void deleteMember(Long userId);
}
