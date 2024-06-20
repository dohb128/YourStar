package com.startup.yourstar.dto;

import lombok.Data;

@Data
public class SubscribeDto {

    private int id;
    private String name;
    private String profileImage;
    private boolean subscribeState;

    public SubscribeDto(int userId, String name, String profileImage, boolean subscribeState) {
        this.id = userId;
        this.name = name;
        this.profileImage = profileImage;
        this.subscribeState = subscribeState;
    }

}
