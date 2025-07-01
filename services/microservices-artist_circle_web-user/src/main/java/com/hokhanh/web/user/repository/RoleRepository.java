package com.hokhanh.web.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hokhanh.web.user.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
