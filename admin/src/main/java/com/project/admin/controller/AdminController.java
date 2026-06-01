package com.project.admin.controller;

import com.project.admin.controller.dto.request.admin.AdminSigninRequest;
import com.project.admin.controller.dto.request.admin.AdminSignupRequest;
import com.project.admin.controller.dto.response.MeResponse;
import com.project.admin.controller.spec.AdminApi;
import com.project.admin.security.handler.LoginResponse;
import com.project.admin.security.userdatails.CustomAdminDetails;
import com.project.admin.service.AdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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
    public LoginResponse signin(AdminSigninRequest request) {

        return LoginResponse.ok();
    }

    @GetMapping("/me")
    public ResponseEntity<MeResponse> me(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated() ||
                !(authentication.getPrincipal() instanceof CustomAdminDetails details)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(new MeResponse(details.getUsername()));

    }
}
