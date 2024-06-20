package com.startup.yourstar.service;

import java.util.HashMap;
import java.util.Map;

import com.startup.yourstar.config.EncryptModule;
import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import com.startup.yourstar.dto.SignupDto;
import com.startup.yourstar.entity.User;
import com.startup.yourstar.repository.UserRepository;

@Service
public class AuthService {
	
	@Autowired
	UserRepository userRepository;

	public boolean login(String username, String password) {
		// Retrieve user from the database based on the username
		User user = userRepository.findByUsername(username);

		// Check if user exists and verify the password
		if (user != null && EncryptModule.getSHA512(password).equals(user.getPassword())) {
			return true; // Authentication successful
		}
		return false; // Authentication failed
	}

	// Error 객체 처리하여 유효성 검사 결과를 Map으로 반환
	@Transactional
	public Map<String, String> validHandling(Errors errors) {
		Map<String, String> validResult = new HashMap<>();
		for(FieldError error : errors.getFieldErrors()) {
			validResult.put("valid_"+error.getField(), error.getDefaultMessage());
		}
		return validResult;
	}

	// Username을 이용해 사용자 존재 여부 확인
	@Transactional
	public boolean findUser(String username) {
		return userRepository.existsByUsername(username);
	}

	// 회원가입 정보 받아 비밀번호 암호화 후 사용자 등록
	public void userSignup(SignupDto signupDto) {
		String encPassword = EncryptModule.getSHA512(signupDto.getPassword());
		signupDto.setPassword(encPassword);
		User user = signupDto.toEntity();
		userRepository.save(user);
		
	}

	// username을 이용하여 사용자 조회
	@Transactional
	public User findByUser(String username) {
		return userRepository.findByUsername(username);
	}


}