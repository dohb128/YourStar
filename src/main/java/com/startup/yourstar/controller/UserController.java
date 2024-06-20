package com.startup.yourstar.controller;

import com.startup.yourstar.dto.UserProfileDto;
import com.startup.yourstar.entity.User;
import com.startup.yourstar.service.UserService;
import com.startup.yourstar.service.AuthService;
import com.startup.yourstar.utils.Script;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class UserController {

    private final UserService userService;
    private final AuthService authService;

    @Autowired
    public UserController(UserService userService, AuthService authService) {
        this.userService = userService;
        this.authService = authService;
    }

    @GetMapping("/user/update/{id}")
    public String updateForm(@PathVariable int id, HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        if (id != (int)session.getAttribute("userId")) {
            return Script.locationMsg("/user/profile", "잘못된 접근입니다", model);
        }

        // 사용자 정보를 userService를 통해 조회
        UserProfileDto user = userService.findById(id);
        model.addAttribute("user", user);

        return "user/update";
    }

    @GetMapping("/user/update")
    public String updateDefault(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        int userId = (int)session.getAttribute("userId");
        return updateForm(userId, request, model);
    }

    @GetMapping("/user/profile")
    public String profileDefault(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        int userId = (int)session.getAttribute("userId");
        return profile(userId, model, session);
    }

    @GetMapping("/user/profile/{id}")
    public String profile(@PathVariable int id, Model model, HttpSession session) {
        int userId = (int) session.getAttribute("userId");
        // 사용자 ID를 통해 프로필 정보를 조회
        UserProfileDto profileDto = userService.findById(id);
        boolean isSubscribe = userService.checkSubscribe(id, userId);
        profileDto.setSubscribeState(isSubscribe);

        if (profileDto == null) {
            System.out.println("=========================ERRRRRRRROOOORRRR=======================");
            // 프로필 정보가 없는 경우 예외 처리
            return "error"; // 에러 페이지로 리다이렉트하거나 적절히 처리
        }

        model.addAttribute("profileDto", profileDto);
        return "user/profile";
    }
}
