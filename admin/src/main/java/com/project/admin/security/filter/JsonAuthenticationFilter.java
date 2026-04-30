package com.project.admin.security.filter;

import com.project.admin.controller.dto.AdminSigninRequest;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jspecify.annotations.Nullable;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import tools.jackson.databind.ObjectMapper;


import java.io.IOException;

public class JsonAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private final ObjectMapper objectMapper;

    public JsonAuthenticationFilter(ObjectMapper objectMapper) {
        super("/api/admin/signin");
        this.objectMapper = objectMapper;
    }

    @Override
    public @Nullable Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException {

        if (!request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("지원하지 않는 메서드 입니다");

        }
        AdminSigninRequest signinRequest = objectMapper.readValue(request.getInputStream(), AdminSigninRequest.class);

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                signinRequest.loginId(), signinRequest.password());


        return this.getAuthenticationManager().authenticate(token);

    }


}
