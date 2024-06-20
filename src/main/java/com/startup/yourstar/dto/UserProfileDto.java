package com.startup.yourstar.dto;

import com.startup.yourstar.entity.User;
import com.startup.yourstar.entity.Image;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileDto {
    private int id;
    private String name;
    private String website;
    private String bio;
    private String profileImage;
    private String email; // Add email property
    private List<Image> images;
    private int imageCount;
    private boolean subscribeState;
    private int subscribeCount;

    /**
     * 엔티티(User)를 DTO(UserProfileDto)로 변환하는 메서드
     * @param user 변환할 엔티티 객체
     * @return 변환된 DTO 객체
     */
    public static UserProfileDto EntityToDto(User user){
        return UserProfileDto.builder()
                .id(user.getId())
                .name(user.getName())
                .website(user.getWebsite())
                .bio(user.getBio())
                .profileImage(user.getProfileImage())
                .email(user.getEmail()) // Set email property
                .images(imageList(user.getImages()))
                .build();
    }

    public static List<Image> imageList(List<Image> images){
        List<Image> imageList = new ArrayList<>();
        for(Image image : images)
            imageList.add(new Image(image));
        return imageList;
    }
}
