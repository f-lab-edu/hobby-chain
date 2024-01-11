package com.hobby.chain.push.service;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

@Service
public class LocaleService {
    private final LocaleResolver localeResolver;
    private final HttpServletRequest request;

    public LocaleService(LocaleResolver localeResolver, HttpServletRequest request) {
        this.localeResolver = localeResolver;
        this.request = request;
    }

    public Locale getCurrentLocale(){
        return localeResolver.resolveLocale(request);
    }
}
