package com.hobby.chain.member.controller;

import com.hobby.chain.member.dto.MemberDTO;
import com.hobby.chain.member.dto.MemberLogin;
import com.hobby.chain.member.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
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

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public void memberLogin(@RequestBody MemberLogin memberLogin, HttpSession session){
        memberService.login(memberLogin.getUserId(), memberLogin.getPassword());
        session.setAttribute("member", memberLogin.getUserId());
    }

    @PostMapping("/logout")
    public void memberLogout(HttpSession session){
        session.invalidate();
    }

    @PostMapping("/me")
    public MemberDTO memberInfo(HttpSession session){
        String userId = String.valueOf(session.getAttribute("member"));
        return memberService.getMemberInfo(userId);
    }
}
