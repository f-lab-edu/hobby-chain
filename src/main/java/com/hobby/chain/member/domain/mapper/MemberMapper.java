package com.hobby.chain.member.domain.mapper;

import com.hobby.chain.member.dto.MemberDTO;
import com.hobby.chain.member.dto.MemberLogin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface MemberMapper {
    boolean exist(@Param("userId") String userId);
    void insertMember(MemberDTO memberDTO);
    MemberLogin findById(String userId);
    MemberDTO getMemberInfo(String userId);
}
