package com.hobby.chain.member.domain.mapper;

import com.hobby.chain.member.domain.entity.Member;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface MemberMapper {
    int exist(@Param("userId") String userId);
    void insertMember(Member member);
}
