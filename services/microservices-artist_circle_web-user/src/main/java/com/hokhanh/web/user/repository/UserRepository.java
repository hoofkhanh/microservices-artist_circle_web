package com.hokhanh.web.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hokhanh.web.user.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	boolean existsByEmail(String email);
	User findByEmail(String email);
}
