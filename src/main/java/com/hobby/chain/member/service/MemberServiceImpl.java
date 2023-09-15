package com.hobby.chain.member.service;

import com.hobby.chain.member.domain.entity.Member;
import com.hobby.chain.member.domain.mapper.MemberMapper;
import com.hobby.chain.member.dto.MemberDTO;
import com.hobby.chain.member.dto.MemberLogin;
import com.hobby.chain.member.exception.DuplicationException;
import com.hobby.chain.member.exception.IncorrectPasswordException;
import com.hobby.chain.member.exception.NotExistUserException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

@Service
public class MemberServiceImpl implements MemberService{
    private final MemberMapper memberMapper;
    private final PasswordEncoder passwordEncoder;

    public MemberServiceImpl(MemberMapper memberMapper, PasswordEncoder passwordEncoder) {
        this.memberMapper = memberMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void signUp(MemberDTO memberDTO) {
        boolean existMember = exist(memberDTO.getUserId());

        if(!existMember) {
            memberMapper.insertMember(Member.builder()
                            .userId(memberDTO.getUserId())
                            .password(passwordEncoder.encode(memberDTO.getPassword()))
                            .name(memberDTO.getName())
                            .nickName(memberDTO.getNickName())
                            .phoneNumber(memberDTO.getPhoneNumber())
                            .gender(memberDTO.getGender())
                            .birth(memberDTO.getBirth())
                            .build());
        } else {
            throw new DuplicationException();
        }
    }

    @Override
    public boolean exist(String userId) {
        return memberMapper.exist(userId);
    }

    @Override
    public boolean login(String userId, String password) {
        MemberLogin loginInfo = memberMapper.findById(userId);

        if(loginInfo == null){
            throw new NotExistUserException();
        } else if (!passwordEncoder.matches(password, loginInfo.getPassword())){
            throw new IncorrectPasswordException();
        }

        return true;
    }

    @Override
    public MemberDTO getMemberInfo(String userId) {
        MemberDTO memberInfo = memberMapper.getMemberInfo(userId);
        return memberInfo;
    }
}
