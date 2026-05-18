package com.project.admin.controller.dto.request.admin;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;


public record AdminSignupRequest(
        @NotBlank(message = "아이디를 입력 해주세요.")
        @Size(min = 5, max = 20, message = "아이디는 5글자 이상, 20글자 이하로 입력 해주세요")
        @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-zA-Z])[a-zA-Z0-9]+$",
                message = "아이디는 영문과 숫자만 사용할 수 있습니다")
        String loginId,

        @NotBlank(message = "비밀번호를 입력 해주세요.")
        @Size(min = 8, max = 20, message = "비밀번호는 8글자 이상 20글자 이하로 입력 해주세요")
        @Pattern(regexp = "^(?=.*[0-9])(?=.*[!@#$%^&*])(?=.*[a-zA-Z])[a-zA-Z0-9!@#$%^&*]+$",
                message = "비밀번호는 영문 포함 숫자와 특수문자를 1개 이상 포함해야 합니다")
        String password,

        @NotBlank(message = "비밀번호 재확인을 입력 해주세요")
        @Size(min = 8, max = 20, message = "8글자 이상 20글자 이하로 입력 해주세요")
        String passwordConfirm

) {
}

