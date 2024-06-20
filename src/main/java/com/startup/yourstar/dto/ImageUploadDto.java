package com.startup.yourstar.dto;

import lombok.Builder;
import org.springframework.web.multipart.MultipartFile;
import com.startup.yourstar.entity.Image;
import lombok.Data;

@Builder
@Data
public class ImageUploadDto {

    private MultipartFile file;
    private String caption;
    private int id;
    private String postImage;

    public Image toEntity(int writerId, String caption, String postImage) {
        return Image.builder()
                .writerId(writerId)
                .caption(caption)
                .postImage(postImage)
                .build();
    }
}