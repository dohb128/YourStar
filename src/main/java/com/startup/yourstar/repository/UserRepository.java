package com.startup.yourstar.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.startup.yourstar.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	
	boolean existsByUsername(String username);

	User findByUsername(String username);

	User findById(int id);
}
