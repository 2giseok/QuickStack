package com.project.admin.domain.service;

import com.project.admin.controller.dto.request.admin.AdminSignupRequest;
import com.project.admin.domain.exception.admin.AdminException;
import com.project.admin.repository.AdminRepository;
import com.project.admin.service.AdminService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class AdminServiceTest {

    @Autowired
    private AdminService adminService;

    @Autowired
    private AdminRepository adminRepository;

    AdminSignupRequest request;

    @BeforeEach
    void setUp() {

        request = new AdminSignupRequest( "admin123", "password","password");
        adminService.createAdmin( request.loginId(), request.password(),request.passwordConfirm());

    }

    @Test
    @DisplayName("관리자 생성 성공")
    void createAdmin() {


        assertThat(adminRepository.existsByLoginId("admin123")).isTrue();


    }

    @Test
    @DisplayName("관리자 생성 실패")
    void createAdmin_fail() throws IllegalStateException {


        assertThat(adminRepository.existsByLoginId("admin123")).isTrue();

        assertThatThrownBy(() -> adminService.createAdmin( request.loginId(), request.password(),request.passwordConfirm())).isInstanceOf(AdminException.class)
                .hasMessage("이미 존재하는 아이디 입니다");


    }


}