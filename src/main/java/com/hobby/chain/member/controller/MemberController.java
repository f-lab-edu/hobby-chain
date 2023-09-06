package com.hobby.chain.member.controller;

import com.hobby.chain.member.dto.MemberDTO;
import com.hobby.chain.member.service.MemberService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class MemberController {
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping
    public void memberSave(@RequestBody MemberDTO memberDTO){
        memberService.signUp(memberDTO);
    }

}
