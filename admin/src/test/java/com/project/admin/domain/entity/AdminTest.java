package com.project.admin.domain.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class AdminTest {


    @Test
    @DisplayName("생성 테스트")
    void createAdmin() {

        Admin admin = Admin.create("name", "admin123", "password");

        assertThat(admin.getName()).isEqualTo("name");
        assertThat(admin.getLoginId()).isEqualTo("admin123");
        assertThat(admin.getPassword()).isEqualTo("password");


    }

}