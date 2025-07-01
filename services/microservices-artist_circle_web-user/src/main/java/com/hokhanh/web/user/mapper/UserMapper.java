package com.hokhanh.web.user.mapper;

import java.time.LocalDateTime;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hokhanh.web.user.model.Provider;
import com.hokhanh.web.user.model.Role;
import com.hokhanh.web.user.model.User;
import com.hokhanh.web.user.request.UserRequest;
import com.hokhanh.web.user.response.RoleResponse;
import com.hokhanh.web.user.response.UserResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserMapper {
	
	private final PasswordEncoder passwordEncoder;

	public User toUser(UserRequest request, Role role) {
		return User.builder()
				.role(role)
				.fullName(request.fullName())
				.email(request.email())
				.password(passwordEncoder.encode(request.password()))
				.isLocked(false)
				.lastActive(LocalDateTime.now())
				.provider(Provider.LOCAL)
				.build();
	}
	
	public UserResponse toUserResponse(User user) {
		return new UserResponse(
					user.getId(),
					new RoleResponse(user.getRole().getId(), user.getRole().getName()),
					user.getFullName(),
					user.getEmail(),
					user.isLocked(),
					user.getLastActive(),
					user.getProvider(),
					user.getCreatedAt()
				);
	}
}
