package com.hobby.chain.member.service;

import com.hobby.chain.member.domain.entity.Member;
import com.hobby.chain.member.dto.MemberDTO;

public interface MemberService {
    public void signUp(MemberDTO memberDTO);
}
