package com.startup.yourstar.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import java.util.List;

import com.startup.yourstar.repository.SubscribeRepository;
import com.startup.yourstar.dto.SubscribeDto;
import com.startup.yourstar.entity.Subscribe;
import com.startup.yourstar.entity.User;

@Service
public class SubscribeService {

    @Autowired
    SubscribeRepository subscribeRepository;

    @Autowired
    private EntityManager em;

    @Transactional
    public void saveSubscribe(int toUserId, int fromUserId) {
        Subscribe subscribe = new Subscribe(toUserId, fromUserId); // 올바른 생성자 사용
        subscribeRepository.save(subscribe);
    }

    @Transactional
    public void deleteSubscribe(int toUserId, int fromUserId) {
        subscribeRepository.deleteByToUserIdAndFromUserId(toUserId, fromUserId); // 올바른 메소드 호출
    }

    public List<SubscribeDto> subscribeList(int toUserId, int principalId) {

        StringBuffer sb = new StringBuffer();
        sb.append("SELECT new com.startup.yourstar.dto.SubscribeDto(u.id, u.name, u.profileImage, ");
        sb.append("CASE WHEN (SELECT 1 FROM Subscribe s WHERE s.fromUserId = :principalId AND s.toUserId = u.id) = 1 THEN true ELSE false END) ");
        sb.append("FROM User u INNER JOIN Subscribe s ON u.id = s.fromUserId "); // 조인 조건 수정
        sb.append("WHERE s.toUserId = :toUserId"); // 조인 조건 및 파라미터 수정

        // Complete the query
        Query query = em.createQuery(sb.toString())
                .setParameter("principalId", principalId)
                .setParameter("toUserId", toUserId);

        // Execute the query and map results to SubscribeDto
        List<SubscribeDto> subscribeDto = query.getResultList();
        return subscribeDto;
    }

}
