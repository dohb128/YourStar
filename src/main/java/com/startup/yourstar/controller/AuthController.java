package com.startup.yourstar.controller;

import java.util.Map;

import com.startup.yourstar.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;  // 변경: Controller 어노테이션 추가
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.startup.yourstar.dto.SignupDto;
import com.startup.yourstar.service.AuthService;
import com.startup.yourstar.utils.Script;

import jakarta.validation.Valid;

@Controller // 스프링 MVC 컨트롤러 컴포넌트로 지정
public class AuthController {

	@Autowired
	private AuthService authService; // AuthService 빈 자동 주입

	@GetMapping("/auth/signin")
	public String signin() {
		return "auth/signin";
	}


	// 로그인 성공 시 처리
	@PostMapping("/auth/signin")
	public String processSignin(@RequestParam String username, @RequestParam String password, Model model, HttpServletRequest request) {
		boolean loginResult = authService.login(username, password);
		HttpSession session = request.getSession();

		if (loginResult) {
			// 로그인 성공한 경우 회원 정보를 DB에서 가져와서 세션에 저장
			User user = authService.findByUser(username);
			if (user != null) {
				// 세션에 회원 아이디 저장
				session.setAttribute("userId", user.getId());
				System.out.println(user.getId());
				return "redirect:/user/profile/" + user.getId();
			} else {
				// 회원 정보가 없는 경우 처리
				model.addAttribute("error", "회원 정보를 찾을 수 없습니다.");
				return "auth/signin";
			}
		} else {
			// 로그인 실패 처리
			model.addAttribute("error", "아이디 또는 비밀번호가 올바르지 않습니다.");
			return "auth/signin";
		}
	}

	// 로그아웃 처리
	@GetMapping("/auth/signout")
	public String signout(HttpServletRequest request) {
		request.getSession().invalidate();	// 세션 무효화
		return "redirect:/auth/signin";
	}


	@GetMapping("/auth/signup")
	public String signup() {
		return "auth/signup";
	}

	@PostMapping("/auth/signup")
	public String processSignup(@Valid SignupDto signupDto, Errors errors, Model model) {
		if (errors.hasErrors()) {
			model.addAttribute("signupDto", signupDto);
			Map<String, String> validResult = authService.validHandling(errors);
			validResult.forEach(model::addAttribute);
			return "auth/signup"; // 유효성 검사 오류가 있는 경우 회원가입 폼을 다시 표시
		}

		if (authService.findUser(signupDto.getUsername())) {
			model.addAttribute("valid_username", "이미 등록된 아이디입니다");
			return "auth/signup"; // 사용자명이 이미 존재하는 경우 회원가입 폼을 다시 표시
		}

		authService.userSignup(signupDto);
		return Script.locationMsg("/auth/signin", "회원가입에 성공하셨습니다", model); // 회원가입 성공 후 로그인 페이지로 리다이렉트
	}

	@GetMapping("/auth/failed")
	public String failedSignin(Model model) {
		return Script.locationMsg("/auth/signin", "아이디 또는 비밀번호를 잘못 입력하셨습니다.", model);
	}
}
