package com.project.admin.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.project.admin.domain.entity.Admin;
import com.project.admin.domain.exception.admin.AdminErrorCode;
import com.project.admin.domain.exception.admin.AdminException;
import com.project.admin.repository.AdminRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class AdminServiceTest {
    @Mock
    private AdminRepository adminRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private AdminService adminService;

    @Test
    void 아이디_생성() {
        when(adminRepository.existsByLoginId("admin123")).thenReturn(false);
        when(passwordEncoder.encode("password")).thenReturn("encoded-password");

        adminService.createAdmin("admin123","password","password");

        ArgumentCaptor<Admin> captor = ArgumentCaptor.forClass(Admin.class);
        verify(adminRepository).save(captor.capture());

        Admin value = captor.getValue();
        assertThat(value.getLoginId()).isEqualTo("admin123");
        assertThat(value.getPassword()).isEqualTo("encoded-password");

    }

    @Test
    void 중복_아이디_발생() {
        when(adminRepository.existsByLoginId("admin123")).thenReturn(true);

        assertThatThrownBy(() -> adminService.createAdmin("admin123","password","password"))
                .isInstanceOf(AdminException.class)
                .extracting("errorCode")
                .isEqualTo(AdminErrorCode.DUPLICATE_LOGIN_ID);
    }

    @Test
    void 비밀번호_틀림_발생() {
        when(adminRepository.existsByLoginId("admin123")).thenReturn(false);

        assertThatThrownBy(() -> adminService.createAdmin("admin123","password","password123"))
                .isInstanceOf(AdminException.class)
                .extracting("errorCode")
                .isEqualTo(AdminErrorCode.PASSWORD_NOT_MATCH);
    }

}