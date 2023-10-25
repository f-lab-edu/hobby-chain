package com.hobby.chain.resolver;

import com.hobby.chain.annotation.CheckLogin;
import com.hobby.chain.member.exception.ForbiddenException;
import com.hobby.chain.member.service.MemberLoginService;
import com.hobby.chain.util.SessionKey;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@RequiredArgsConstructor
@Component
public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {
    private final MemberLoginService loginService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(CheckLogin.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        try{
            return loginService.getLoginMemberIdx();
        } catch (ForbiddenException exception){
            throw exception;
        }
    }
}
