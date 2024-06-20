package com.startup.yourstar.controller;

import com.startup.yourstar.dto.ImageUploadDto;
import com.startup.yourstar.entity.Image;
import com.startup.yourstar.service.ImageService;
import com.startup.yourstar.utils.Script;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ImageController {

    @Autowired
    private ImageService imageService;

    @GetMapping("/image/upload")
    public String upload() {
        return "image/upload";
    }

    @GetMapping("/image/upload/{postId}")
    public String upDate(@PathVariable int postId, Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        int userId = (int)session.getAttribute("userId");
        Image image = imageService.findById(postId);
        ImageUploadDto imageUploadDto = image.toDto();

        if(image.getWriterId() != userId)
            return Script.locationMsg("/user/profile/"+userId, "잘못된 접근입니다", model);

        model.addAttribute("image", imageUploadDto);

        return "image/upload";
    }
}