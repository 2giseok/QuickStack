package com.project.admin.controller.spec;

import com.project.admin.controller.dto.request.admin.AdminSigninRequest;
import com.project.admin.controller.dto.request.admin.AdminSignupRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "signin/signup")
public interface AdminApi {

    @Operation(summary = "관리자 회원가입")
    @ApiResponse(responseCode = "201")
    ResponseEntity<Void> signup(@Valid @RequestBody AdminSignupRequest request);

    @Operation(summary = "관리자 로그인")
    void signin(AdminSigninRequest request);



}
