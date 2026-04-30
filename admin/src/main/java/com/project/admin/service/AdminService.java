package com.project.admin.service;

import com.project.admin.domain.entity.Admin;
import com.project.admin.repository.AdminRepository;
import com.project.admin.domain.exception.AdminErrorCode;
import com.project.admin.domain.exception.AdminException;
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
    public void createAdmin(String name, String loginId, String password) {


        if (adminRepository.existsByLoginId(loginId)) {

            throw new AdminException(AdminErrorCode.DUPLICATE_LOGIN_ID);
        }


        String encodedPassword = passwordEncoder.encode(password);


        Admin admin = Admin.create(name, loginId, encodedPassword);

        adminRepository.save(admin);


    }


}
