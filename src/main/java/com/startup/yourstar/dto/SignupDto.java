package com.startup.yourstar.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import com.startup.yourstar.entity.User;

import lombok.Data;

@Data
public class SignupDto {
	
	@NotBlank(message = "아이디를 입력해주세요")
	@Pattern(regexp = "^[ㄱ-ㅎ가-힣a-z0-9-_]{5,30}$", message = "아이디는 특수문자를 제외한<br>5자이상이여야 합니다")
	private String username;
	
	@NotBlank(message = "비밀번호를 입력해주세요")
	@Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z]).{8,16}", message = "최소 하나의 문자 및 숫자를 포함한<br>8~16자이여야 합니다")
	private String password;
	
	@NotBlank
	@Pattern(regexp = "^[ㄱ-ㅎ가-힣a-z]{2,30}$", message = "숫자 또는 특수문자를 제외한<br>2자이상 입력해주세요")
	private String name;
	
	@NotBlank(message = "이메일을 입력해주세요")
	@Pattern(regexp = "^(?:\\w+\\.?)*\\w+@(?:\\w+\\.)+\\w+$", message = "이메일 형식이 올바르지 않습니다") 
	private String email;
	
	public User toEntity() {
		return User.builder()
				.username(username)
				.password(password)
				.email(email)
				.name(name)
				.role("ROLE_USER")
				.build();
	}
}