package com.hobby.chain.member.controller;

import com.hobby.chain.member.dto.MemberDTO;
import com.hobby.chain.member.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
public class MemberController {
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void memberSave(@RequestBody @Valid MemberDTO memberDTO){
        memberService.signUp(memberDTO);
    }

}
