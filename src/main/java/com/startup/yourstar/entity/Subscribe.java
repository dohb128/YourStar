package com.startup.yourstar.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Subscribe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "to_user_id", nullable = false)
    private int toUserId;

    @Column(name = "from_user_id", nullable = false)
    private int fromUserId;

    @Column(name = "create_date", nullable = false)
    private LocalDateTime createDate;

    // 필요한 생성자 추가
    public Subscribe(int toUserId, int fromUserId) {
        this.toUserId = toUserId;
        this.fromUserId = fromUserId;
        this.createDate = LocalDateTime.now(); // 생성 시 현재 시간으로 설정
    }
}
