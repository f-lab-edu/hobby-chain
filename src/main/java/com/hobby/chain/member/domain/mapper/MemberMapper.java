package com.hobby.chain.member.domain.mapper;

import com.hobby.chain.member.dto.MemberDTO;
import com.hobby.chain.member.dto.MemberInfo;
import com.hobby.chain.member.dto.MemberLogin;
import com.hobby.chain.member.dto.UpdateRequestInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface MemberMapper {
    boolean exist(@Param("email") String email);
    void insertMember(MemberDTO memberDTO);
    MemberLogin findById(String email);
    MemberInfo getMemberInfo(long userId);
    void updateMemberInfo(MemberInfo memberInfo);
    void deleteMember(@Param("userId") long userId);
}
