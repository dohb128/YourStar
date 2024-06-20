package com.startup.yourstar.api;

import com.startup.yourstar.service.SubscribeService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class SubscribeApiController {

    @Autowired
    SubscribeService subscribeService;

    @PostMapping("/api/subscribe/{toUserId}")
    public int sub(@PathVariable int toUserId, HttpSession session, int isWhat) {
        int userId = (int)session.getAttribute("userId");
        System.out.println("ID: " + userId + " // toUserId : " + toUserId + " /// isWhat : " + isWhat);
        int resultCode;
        if(isWhat == 0) {
            subscribeService.saveSubscribe(toUserId, userId);
            resultCode = 0;
        } else if(isWhat == 1) {
            subscribeService.deleteSubscribe(toUserId, userId);
            resultCode = 0;
        } else {
            resultCode = 1;
        }
        return resultCode;
    }
}
