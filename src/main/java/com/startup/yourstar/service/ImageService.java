package com.startup.yourstar.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.startup.yourstar.dto.ImageUploadDto;
import com.startup.yourstar.entity.Image;
import com.startup.yourstar.repository.ImageRepository;

@Service
public class ImageService {

    @Autowired
    ImageRepository imageRepository;


    @Transactional
    public boolean postImage(int postId, ImageUploadDto imageUploadDto, int principalId) {
        if (imageUploadDto.getFile() != null) {
            String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyMMdd"));
            String uploadFolder = Paths.get("C:", "insta", "upload").toString();
            String profileUploadFolder = Paths.get("FeedImage", today).toString();
            String uploadPath = Paths.get(uploadFolder, profileUploadFolder).toString();

            File dir = new File(uploadPath);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            UUID uuid = UUID.randomUUID();
            String profileImageName = uuid + "_" + imageUploadDto.getFile().getOriginalFilename();
            String postImage = profileUploadFolder + "/" + profileImageName;

            try {
                File target = new File(uploadPath, profileImageName);
                imageUploadDto.getFile().transferTo(target);
            } catch (IOException e) {
                return false;
            }

            Image image;
            if (postId == 0) {
                image = imageUploadDto.toEntity(principalId, imageUploadDto.getCaption(), postImage);
            } else {
                image = imageRepository.findById(postId);
                if (image == null) {
                    return false;
                }
                // 이미지 필드 값 업데이트
                image.setCaption(imageUploadDto.getCaption());
                image.setPostImage(postImage);
            }

            imageRepository.save(image);
            return true;
        }

        return false;
    }



    @Transactional
    public boolean deleteImage(int postId) {
        imageRepository.deleteById(postId);
        return true;
    }

    @Transactional
    public Image findById(int imageId) {
        Image image = imageRepository.findById(imageId);
        return image;
    }
}