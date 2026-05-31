package com.project.admin.controller;

import com.project.admin.controller.dto.request.admin.AdminSigninRequest;
import com.project.admin.controller.dto.request.admin.AdminSignupRequest;
import com.project.admin.controller.spec.AdminApi;
import com.project.admin.security.handler.LoginResponse;
import com.project.admin.service.AdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AdminController implements AdminApi {

    private final AdminService adminService;

    @PostMapping("/signup")
    public ResponseEntity<Void> signup(@Valid @RequestBody AdminSignupRequest request) {
        adminService.createAdmin(request.loginId(), request.password(), request.passwordConfirm());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/signin")
    public LoginResponse signin ( AdminSigninRequest request) {

        return LoginResponse.ok();
    }
}
