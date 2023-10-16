package com.hobby.chain.member.service;

import com.hobby.chain.member.domain.mapper.MemberMapper;
import com.hobby.chain.member.dto.MemberDTO;
import com.hobby.chain.member.dto.MemberInfo;
import com.hobby.chain.member.dto.MemberLogin;
import com.hobby.chain.member.dto.UpdateRequestInfo;
import com.hobby.chain.member.exception.DuplicationException;
import com.hobby.chain.member.exception.ForbiddenException;
import com.hobby.chain.member.exception.IncorrectPasswordException;
import com.hobby.chain.member.exception.NotExistUserException;
import com.hobby.chain.util.SessionKey;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void signUp(MemberDTO memberDTO) {
        boolean existMember = exist(memberDTO.getEmail());

        if(!existMember) {
            memberMapper.insertMember(buildMemberDto(memberDTO));
        } else {
            throw new DuplicationException();
        }
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    public boolean exist(String userId) {
        return memberMapper.isExistMemberByEmail(userId);
    }

    @Override
    public void login(String userId, String password) {
        MemberLogin loginInfo = memberMapper.findById(userId);

        if(loginInfo == null){
            throw new NotExistUserException();
        } else if (!passwordEncoder.matches(password, loginInfo.getPassword())){
            throw new IncorrectPasswordException();
        }

        session.setAttribute(SessionKey.MEMBER_IDX, loginInfo.getUserId());
    }

    @Override
    public void logout() {
        session.removeAttribute(SessionKey.MEMBER_IDX);
    }

    @Override
    public long getLoginMemberIdx() throws ForbiddenException{
        Object userId = session.getAttribute(SessionKey.MEMBER_IDX);
        if(userId != null){
            return (long) userId;
        } else {
            throw new ForbiddenException("로그인이 필요한 기능입니다.");
        }
    }

    @Override
    public MemberInfo getMemberInfo() {
        return memberMapper.getMemberInfo(getLoginMemberIdx());
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void updateMemberInfo(UpdateRequestInfo requestInfo){
        MemberInfo memberInfo = buildMemberInfo(requestInfo);
        memberMapper.updateMemberInfo(memberInfo);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void deleteMember() {
        memberMapper.deleteMember(getLoginMemberIdx());
        logout();
    }
  
    private MemberDTO buildMemberDto(MemberDTO memberDTO){
        return MemberDTO.builder()
                .email(memberDTO.getEmail())
                .password(passwordEncoder.encode(memberDTO.getPassword()))
                .name(memberDTO.getName())
                .nickName(memberDTO.getNickName())
                .phoneNumber(memberDTO.getPhoneNumber())
                .gender(memberDTO.getGender())
                .birth(memberDTO.getBirth())
                .build();
    }

    private MemberInfo buildMemberInfo(UpdateRequestInfo requestInfo){
        return MemberInfo.builder().
                userId(getLoginMemberIdx())
                .nickName(requestInfo.getNickName())
                .phoneNumber(requestInfo.getPhoneNumber())
                .gender(requestInfo.getGender())
                .birth(requestInfo.getBirth())
                .build();
    }

    @Override
    public boolean isExistUser(long userId) {
        return memberMapper.isExistMemberById(userId);
    }
}
