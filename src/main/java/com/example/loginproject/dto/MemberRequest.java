package com.example.loginproject.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberRequest {

    @NotBlank(message = "아이디는 필수입니다.")
    private String username;

    @NotBlank(message = "비밀번호는 필수입니다.")
    private String password;

    @NotBlank(message = "비밀번호 확인은 필수입니다.")
    private String confirmPassword;

    @NotBlank(message = "주민등록번호는 필수입니다.")
    @Pattern(regexp = "\\d{6}-\\d{7}", message = "주민등록번호 형식이 올바르지 않습니다.")
    private String ssn;

    @NotBlank(message = "전화번호는 필수입니다.")
    @Pattern(regexp = "\\d{3}-\\d{3,4}-\\d{4}", message = "전화번호 형식이 올바르지 않습니다.")
    private String phoneNumber;
}
