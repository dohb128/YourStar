package com.startup.yourstar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.startup.yourstar.entity.Image;
public interface ImageRepository extends JpaRepository<Image, Integer> {
    Image findById(int postId);
}
