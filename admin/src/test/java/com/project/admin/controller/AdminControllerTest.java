package com.project.admin.controller;

import com.project.admin.controller.dto.AdminSigninRequest;
import com.project.admin.controller.dto.AdminSignupRequest;
import com.project.admin.domain.entity.Admin;
import com.project.admin.domain.exception.AdminErrorCode;
import com.project.admin.domain.exception.AdminException;
import com.project.admin.security.userdatails.AdminUserDetailsService;
import com.project.admin.security.userdatails.CustomAdminDetails;
import com.project.admin.service.AdminService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class AdminControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    AdminService adminService;

    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    AdminUserDetailsService adminUserDetailsService;

    @Autowired
    PasswordEncoder passwordEncoder;

    Admin admin;

    @BeforeEach
    void init() {

        admin = Admin.create("name",
                "admin123",
                passwordEncoder.encode("password"));

    }


    @Test
    @DisplayName("로그인 성공")
    void signin() throws Exception {

        AdminSigninRequest request = new AdminSigninRequest("admin123", "password");


        CustomAdminDetails details = new CustomAdminDetails(admin);
        given(adminUserDetailsService.loadUserByUsername(request.loginId())).willReturn(details);

        mockMvc.perform(post("/api/admin/signin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("name"));

    }


    @Test
    @DisplayName("회원가입 성공")
    void signup() throws Exception {

        AdminSignupRequest request = new AdminSignupRequest("name", "admin123", "password1@");

        mockMvc.perform(post("/admin/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());

    }

    @Test
    @DisplayName("회원가입 실패")
    void signup_fail() throws Exception {

        AdminSignupRequest request = new AdminSignupRequest("name", "admin123", "password");

        doThrow(new AdminException(AdminErrorCode.DUPLICATE_LOGIN_ID))
                .when(adminService).createAdmin(any(), any(), any());

        mockMvc.perform(post("/admin/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());

    }

}