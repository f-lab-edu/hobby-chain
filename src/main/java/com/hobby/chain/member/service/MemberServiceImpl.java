package com.hobby.chain.member.service;

import com.hobby.chain.member.domain.mapper.MemberMapper;
import com.hobby.chain.member.dto.MemberDTO;
import com.hobby.chain.member.dto.MemberLogin;
import com.hobby.chain.member.exception.DuplicationException;
import com.hobby.chain.member.exception.IncorrectPasswordException;
import com.hobby.chain.member.exception.NotExistUserException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
public class MemberServiceImpl implements MemberService, MemberLoginService{
    private final MemberMapper memberMapper;
    private final PasswordEncoder passwordEncoder;
    private final HttpSession session;

    public MemberServiceImpl(MemberMapper memberMapper, PasswordEncoder passwordEncoder, HttpSession session) {
        this.memberMapper = memberMapper;
        this.passwordEncoder = passwordEncoder;
        this.session = session;
    }

    @Override
    public void signUp(MemberDTO memberDTO) {
        boolean existMember = exist(memberDTO.getUserId());

        if(!existMember) {
            memberMapper.insertMember(MemberDTO.builder()
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
    public void login(String userId, String password) {
        MemberLogin loginInfo = memberMapper.findById(userId);

        if(loginInfo == null){
            throw new NotExistUserException();
        } else if (!passwordEncoder.matches(password, loginInfo.getPassword())){
            throw new IncorrectPasswordException();
        }

        session.setAttribute("member", userId);
    }

    @Override
    public void logout() {
        session.removeAttribute("member");
    }

    @Override
    public String getLoginMemberId() {
        try {
            return String.valueOf(session.getAttribute("medmber"));
        } catch (NullPointerException ne){
            throw new NullPointerException("비로그인 회원입니다.");
        }
    }

    @Override
    public MemberDTO getMemberInfo() {
        return memberMapper.getMemberInfo(getLoginMemberId());
    }
}
