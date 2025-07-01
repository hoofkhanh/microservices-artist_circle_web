package com.hokhanh.web.user.response;

import java.time.LocalDateTime;

import com.hokhanh.web.user.model.Provider;

public record UserResponse(
	Long id,
	RoleResponse role,
	String fullName,
	String email,
	boolean isLocked,
	LocalDateTime lastActive,
	Provider provider,
	LocalDateTime createdAt
) {

}
