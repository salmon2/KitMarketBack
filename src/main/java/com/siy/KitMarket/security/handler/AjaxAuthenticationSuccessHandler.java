package com.siy.KitMarket.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.siy.KitMarket.domain.dto.account.AccountDto;
import com.siy.KitMarket.domain.entity.account.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.RememberMeServices;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AjaxAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    RememberMeServices rememberMeService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException{
        AccountDto accountdto = new AccountDto((Account)authentication.getPrincipal());

        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        rememberMeService.loginSuccess(request, response, authentication);

        objectMapper.writeValue(response.getWriter(), accountdto);
    }
}

