package com.project.admin.service;

import com.project.admin.domain.entity.Admin;
import com.project.admin.repository.AdminRepository;
import com.project.admin.domain.exception.admin.AdminErrorCode;
import com.project.admin.domain.exception.admin.AdminException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminService {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void createAdmin(String loginId, String password, String passwordConfirm) {

        if (adminRepository.existsByLoginId(loginId)) {
            throw new AdminException(AdminErrorCode.DUPLICATE_LOGIN_ID);
        }

        if (!password.equals(passwordConfirm)) {
            throw new AdminException(AdminErrorCode.PASSWORD_NOT_MATCH);
        }

        String encodedPassword = passwordEncoder.encode(password);
        Admin admin = Admin.create(loginId, encodedPassword);
        adminRepository.save(admin);
    }
}
