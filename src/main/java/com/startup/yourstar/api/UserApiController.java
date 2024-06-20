package com.startup.yourstar.api;

import java.util.List;
import java.util.Map;

import com.startup.yourstar.dto.SubscribeDto;
import com.startup.yourstar.service.SubscribeService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonObject;
import com.startup.yourstar.dto.UserUpdateDto;
import com.startup.yourstar.entity.User;
import com.startup.yourstar.service.AuthService;
import com.startup.yourstar.service.UserService;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class UserApiController {

    @Autowired
    UserService userService;
    AuthService authService;
    SubscribeService subscribeService;


    @PutMapping("/api/user/{id}")
    public ResponseEntity<JsonObject> userupdate(@PathVariable int id, @Valid UserUpdateDto userUpdateDto, Errors errors, HttpServletRequest request) {
        JsonObject jsonObj = new JsonObject();
        HttpSession session = request.getSession();

        if(errors.hasErrors()) {
            //유효성검사에 실패한 필드와 메시지를 저장
            Map<String, String> validResult = authService.validHandling(errors);

            //필드를 key값으로 에러메시지 저장
            for(String key : validResult.keySet()) {
                jsonObj.addProperty(key, validResult.get(key));
            }
            return ResponseEntity.badRequest().body(jsonObj);
        }

        User user = userService.updateUser(userUpdateDto);
        //customUser.setPassword(user.getPassword());
        return ResponseEntity.ok().body(jsonObj);

    }

    @PutMapping("/api/user/profileImage/{id}")
    public ResponseEntity<String> updateProfileImage(@PathVariable int id, MultipartFile profileImageFile){
        if(userService.updateProfileImage(id, profileImageFile)) {
            return ResponseEntity.ok().body("프로필 사진 수정에 성공하였습니다");
        }
        else {
            return ResponseEntity.badRequest().body("프로필 사진 수정에 실패하였습니다");
        }
    }

    @GetMapping("/api/user/{pageUserId}/subscribe")
    public ResponseEntity<?> subscribeList(@PathVariable int pageUserId, HttpSession session){

        Integer userId = (Integer) session.getAttribute("USER_ID");
        // 세션에 사용자 정보가 없는 경우 (사용자가 로그인하지 않은 경우)
        if(userId == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
        }

        // 구독 정보 조회 서비스 호출
        List<SubscribeDto> subscribeDto = subscribeService.subscribeList(pageUserId, userId);

        return ResponseEntity.ok().body(subscribeDto);
    }

}