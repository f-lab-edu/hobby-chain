package com.hobby.chain.member.service;

import com.hobby.chain.member.domain.mapper.MemberMapper;
import com.hobby.chain.member.dto.MemberDTO;
import com.hobby.chain.member.dto.MemberLogin;
import com.hobby.chain.member.exception.DuplicationException;
import com.hobby.chain.member.exception.IncorrectPasswordException;
import com.hobby.chain.member.exception.NotExistUserException;
import com.hobby.chain.util.SessionKey;
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

        session.setAttribute(SessionKey.MEMBER_IDX, loginInfo.getIdx());
    }

    @Override
    public void logout() {
        session.removeAttribute(SessionKey.MEMBER_IDX);
    }

    @Override
    public long getLoginMemberIdx() {
        Object userId = session.getAttribute(SessionKey.MEMBER_IDX);
        if(userId != null){
            return (long) userId;
        } else {
            throw new NullPointerException();
        }
    }

    @Override
    public MemberDTO getMemberInfo() {
        return memberMapper.getMemberInfo(getLoginMemberIdx());
    }
}
