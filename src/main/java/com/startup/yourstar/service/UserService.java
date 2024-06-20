package com.startup.yourstar.service;

import com.startup.yourstar.dto.UserProfileDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.startup.yourstar.dto.UserUpdateDto;
import com.startup.yourstar.entity.User;
import com.startup.yourstar.repository.UserRepository;
import com.startup.yourstar.repository.SubscribeRepository;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final SubscribeRepository subscribeRepository;

    @Autowired
    public UserService(UserRepository userRepository, SubscribeRepository subscribeRepository) {
        this.userRepository = userRepository;
        this.subscribeRepository = subscribeRepository;
    }

    // 사용자 정보 업데이트 수행
    @Transactional
    public User updateUser(UserUpdateDto userUpdateDto) {
        User user = userRepository.findByUsername(userUpdateDto.getUsername());

        // 사용자 정보 업데이트
        user.update(userUpdateDto.getPassword(), userUpdateDto.getName(), userUpdateDto.getPhone(),
                userUpdateDto.getGender(), userUpdateDto.getWebsite(), userUpdateDto.getBio(),
                userUpdateDto.getEmail());

        return user;
    }

    @Transactional
    public boolean checkSubscribe(int toUserId, int userId) {
        int check = subscribeRepository.checkSubscribe(toUserId, userId);
        return (check == 1) ? true : false;
    }


    // 사용자 id를 이용해 프로필 정보 조회
    @Transactional
    public UserProfileDto findById(int id) {
        User user = userRepository.findById(id);
        if (user == null) {
            throw new IllegalArgumentException("User not found for id: " + id);
        }
        UserProfileDto profileDto = new UserProfileDto().EntityToDto(user);
        profileDto.setImageCount(user.getImages().size());

        return profileDto;
    }

    // 사용자 id와 principalId를 이용해 프로필 정보 조회
    @Transactional
    public UserProfileDto findById(int profileUserId, int principalId) {
        User user = userRepository.findById(profileUserId);
        if (user == null) {
            throw new IllegalArgumentException("User not found for id: " + profileUserId);
        }
        UserProfileDto profileDto = new UserProfileDto().EntityToDto(user);
        profileDto.setSubscribeCount(subscribeRepository.countByToUserId(profileUserId));
        profileDto.setImageCount(user.getImages().size());
        profileDto.setSubscribeState(subscribeRepository.existsByToUserIdAndFromUserId(profileUserId, principalId));

        return profileDto;
    }

    // 프로필 이미지 업데이트
    @Transactional
    public boolean updateProfileImage(int id, MultipartFile profileImage) {
        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyMMdd"));
        String uploadFolder = Paths.get("C:", "insta", "upload").toString();
        String profileUploadFolder = Paths.get("profileImage", today).toString();
        String uploadPath = Paths.get(uploadFolder, profileUploadFolder).toString();

        File dir = new File(uploadPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        UUID uuid = UUID.randomUUID();
        String profileImageName = uuid + "_" + profileImage.getOriginalFilename();

        try {
            File target = new File(uploadPath, profileImageName);
            profileImage.transferTo(target);
        } catch (Exception e) {
            return false;
        }

        User user = userRepository.findById(id);
        user.updateProfileImage(profileUploadFolder + "\\" + profileImageName);

        return true;
    }
}
