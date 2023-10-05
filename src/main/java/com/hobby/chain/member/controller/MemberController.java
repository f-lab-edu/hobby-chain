package com.hobby.chain.member.controller;

import com.hobby.chain.member.dto.MemberDTO;
import com.hobby.chain.member.dto.MemberInfo;
import com.hobby.chain.member.dto.MemberLogin;
import com.hobby.chain.member.dto.UpdateRequestInfo;
import com.hobby.chain.member.service.MemberLoginService;
import com.hobby.chain.member.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
public class MemberController {
    private final MemberService memberService;
    private final MemberLoginService loginService;

    public MemberController(MemberService memberService, MemberLoginService loginService) {
        this.memberService = memberService;
        this.loginService = loginService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createMember(@RequestBody @Valid MemberDTO memberDTO){
        memberService.signUp(memberDTO);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public void login(@RequestBody MemberLogin memberLogin){
        loginService.login(memberLogin.getEmail(), memberLogin.getPassword());
    }

    @PostMapping("/logout")
    public void logout(){
        loginService.logout();
    }

    @GetMapping("/me")
    public MemberInfo getMemberInfo(){
        return memberService.getMemberInfo();
    }

    @PutMapping("/edit")
    public void updateMemberInfo(@RequestBody UpdateRequestInfo requestInfo){
        memberService.updateMemberInfo(requestInfo);
    }

    @DeleteMapping
    public void deleteMember(){
        memberService.deleteMember();
    }
}
