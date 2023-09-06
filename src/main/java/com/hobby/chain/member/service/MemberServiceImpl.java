package com.hobby.chain.member.service;

import com.hobby.chain.member.domain.entity.Member;
import com.hobby.chain.member.domain.mapper.MemberMapper;
import com.hobby.chain.member.dto.MemberDTO;
import com.hobby.chain.member.exception.DuplicationException;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl implements MemberService{
    private final MemberMapper memberMapper;

    public MemberServiceImpl(MemberMapper memberMapper) {
        this.memberMapper = memberMapper;
    }

    @Override
    public void signUp(MemberDTO memberDTO) {
        boolean notExistMember = isNotExistMember(memberDTO.getUserId());
        if(notExistMember) {
            memberMapper.insertMember(Member.builder()
                            .userId(memberDTO.getUserId())
                            .password(memberDTO.getPassword())
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

    private boolean isNotExistMember(String userId) {
        int isYn = memberMapper.findById(userId);
        if(isYn == 0)  return true;
        return false;
    }

}
