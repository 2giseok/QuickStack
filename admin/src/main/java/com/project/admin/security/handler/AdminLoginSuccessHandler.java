package com.project.admin.security.handler;

import com.project.admin.controller.dto.AdminSigninResponse;
import com.project.admin.security.userdatails.CustomAdminDetails;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;

public class AdminLoginSuccessHandler implements AuthenticationSuccessHandler {

    private final ObjectMapper objectMapper;

    public AdminLoginSuccessHandler(ObjectMapper objectMapper) {

        this.objectMapper = objectMapper;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpServletResponse.SC_OK);

        CustomAdminDetails adminDetails = (CustomAdminDetails) authentication.getPrincipal();

        AdminSigninResponse responseDto = new AdminSigninResponse(
                adminDetails.getAdminId(),
                adminDetails.getName()
        );
        String result = objectMapper.writeValueAsString(responseDto);

        response.getWriter().write(result);
    }
}
