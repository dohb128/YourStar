package com.startup.yourstar.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.startup.yourstar.dto.SubscribeDto;
import com.startup.yourstar.entity.Subscribe;
import org.springframework.transaction.annotation.Transactional;

public interface SubscribeRepository extends JpaRepository<Subscribe, Integer> {

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO subscribe (to_user_id, from_user_id, create_date) VALUES (:toUserId, :fromUserId, NOW())", nativeQuery = true)
    void saveSubscribe(@Param("toUserId") int toUserId, @Param("fromUserId") int fromUserId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM subscribe WHERE to_user_id = :toUserId AND from_user_id = :fromUserId", nativeQuery = true)
    void deleteByToUserIdAndFromUserId(@Param("toUserId") int toUserId, @Param("fromUserId") int fromUserId);

    @Transactional
    @Query(value = "SELECT COUNT(*) FROM subscribe s WHERE s.to_user_id = :toUserId and s.from_user_id = :fromUserId", nativeQuery = true)
    int checkSubscribe(@Param("toUserId") int toUserId, @Param("fromUserId") int fromUserId);

    int countByToUserId(int toUserId);

    boolean existsByToUserIdAndFromUserId(int toUserId, int fromUserId);

    @Query("SELECT DISTINCT s FROM Subscribe s WHERE s.toUserId = :toUserId")
    List<SubscribeDto> findByToUserId(@Param("toUserId") int toUserId);

    List<Subscribe> findByFromUserId(int fromUserId);
}
