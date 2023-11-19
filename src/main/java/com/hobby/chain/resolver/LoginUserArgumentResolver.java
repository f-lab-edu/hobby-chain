package com.hobby.chain.resolver;

import com.hobby.chain.member.exception.ForbiddenException;
import com.hobby.chain.util.SessionKey;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Component
public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {
    private final HttpSession session;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameter().equals(Long.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory){
        Object userId = session.getAttribute(SessionKey.MEMBER_IDX);
        if(userId != null){
            return userId;
        } else {
            throw new ForbiddenException();
        }
    }
}
