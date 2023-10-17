package com.hobby.chain.member.service;

public interface MemberLoginService {
    public void login(String userId, String password);
    public void logout();
    public long getLoginMemberIdx();

    public void loginCheck(long userId);
}
