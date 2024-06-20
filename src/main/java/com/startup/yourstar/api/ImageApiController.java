package com.startup.yourstar.api;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.startup.yourstar.dto.ImageUploadDto;
import com.startup.yourstar.entity.Image;
import com.startup.yourstar.service.ImageService;

@RestController
public class ImageApiController {

    @Autowired
    ImageService imageService;

    @PostMapping("/api/image")
    public ResponseEntity<String> imageUpload(ImageUploadDto imageUploadDto, HttpServletRequest request) {
        HttpSession session = request.getSession();

        if (!imageService.postImage(0, imageUploadDto, (int) session.getAttribute("userId")))
            return ResponseEntity.badRequest().body("게시글 작성에 실패하였습니다");


        return ResponseEntity.ok().body("게시글 작성이 완료되었습니다");

    }

    @PutMapping("/api/image/{postId}")
    public ResponseEntity<String> imageUpdate(@PathVariable int postId, ImageUploadDto imageUploadDto, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Image image = imageService.findById(postId);
        int userId = (int) session.getAttribute("userId");

        if (image.getWriterId() != userId)
            return ResponseEntity.badRequest().body("잘못된 접근입니다");

        if (!imageService.postImage(postId, imageUploadDto, userId))
            return ResponseEntity.badRequest().body("게시글 수정에 실패하였습니다");


        return ResponseEntity.ok().body("게시글 수정이 완료되었습니다");
    }

    @DeleteMapping("/api/image/{postId}")
    public ResponseEntity<String> imageDelete(@PathVariable int postId, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Image image = imageService.findById(postId);
        int userId = (int) session.getAttribute("userId");

        if (image.getWriterId() != userId)
            return ResponseEntity.badRequest().body("잘못된 접근입니다");


        if (!imageService.deleteImage(postId))
            return ResponseEntity.badRequest().body("게시글 삭제에 실패하였습니다");


        return ResponseEntity.ok().body("게시글 삭제가 완료되었습니다");
    }

}