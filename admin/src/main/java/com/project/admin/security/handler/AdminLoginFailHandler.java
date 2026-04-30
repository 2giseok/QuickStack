package com.project.admin.security.handler;

import com.project.admin.domain.exception.AdminErrorCode;
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

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        ErrorResponse errorResponse = ErrorResponse.from(AdminErrorCode.LOGIN_FAILED);
        response.setStatus(errorResponse.getStatus());
        String result = objectMapper.writeValueAsString(errorResponse);
        response.getWriter().write(result);
    }
}
