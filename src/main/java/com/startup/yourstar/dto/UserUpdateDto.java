package com.startup.yourstar.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import lombok.Data;

@Data
public class UserUpdateDto {

    private String username;

    @NotBlank(message = "비밀번호를 입력해주세요")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z]).{8,16}", message = "최소 하나의 문자 및 숫자를 포함한 8~16자이여야 합니다")
    private String password;

    @NotBlank
    @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-z]{2,30}$", message = "숫자 또는 특수문자를 제외한 2자이상 입력해주세요")
    private String name;

    @NotBlank(message = "이메일을 입력해주세요")
    @Pattern(regexp = "^(?:\\w+\\.?)*\\w+@(?:\\w+\\.)+\\w+$", message = "이메일 형식이 올바르지 않습니다")
    private String email;

    private String website;
    private String bio;
    private String phone;
    private String gender;

}
