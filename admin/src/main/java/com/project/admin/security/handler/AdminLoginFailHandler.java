package com.project.admin.security.handler;

import com.project.admin.domain.exception.admin.AdminErrorCode;
import com.project.admin.exception.ErrorResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;

public class AdminLoginFailHandler implements AuthenticationFailureHandler {
    private final ObjectMapper objectMapper;

    public AdminLoginFailHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        response.setStatus(400);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String result = objectMapper.writeValueAsString(LoginResponse.fall());
        response.getWriter().write(result);
    }
}
