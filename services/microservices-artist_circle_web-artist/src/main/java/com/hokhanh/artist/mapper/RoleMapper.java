package com.hokhanh.artist.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.hokhanh.artist.model.Role;
import com.hokhanh.artist.response.artist.common.RoleResponse;

@Service
public class RoleMapper {
	public List<RoleResponse> toRoleResponseList(List<Role> roles) {
		return roles
				.stream()
				.map(role -> new RoleResponse(role.getId(), role.getName()))
				.collect(Collectors.toList());
	}
}
