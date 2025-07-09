package com.hokhanh.artist.controller;

import org.springframework.graphql.data.method.annotation.ContextValue;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

import com.hokhanh.artist.service.CloudinaryService;
import com.hokhanh.common.cloudinary.dto.CloudinaryUploadApiResponse;
import com.hokhanh.common.constant.RoleConstants;
import com.hokhanh.common.graphQL.HttpHeadersConstants;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class UploadController {
	private final CloudinaryService cloudinaryService;
	
	@MutationMapping
	public CloudinaryUploadApiResponse createCloudinarySignatureInAvatarUpload(
		@ContextValue(name = HttpHeadersConstants.HEADER_USER_ID) String userId,
		@ContextValue(name = HttpHeadersConstants.HEADER_USER_ROLE) String userRole
	) {
		CloudinaryUploadApiResponse error = checkArtistRole(userRole);
		return  error != null ?error : cloudinaryService.createSignatureInAvatarUpload(userId);
	}
	
	private CloudinaryUploadApiResponse checkArtistRole(String userRole) {
		if(!RoleConstants.ARTIST_ROLE.equals(userRole)) {
			return new CloudinaryUploadApiResponse(false, "ONLY ARTIST ROLE CAN ACCESS THIS API", null);
		}
		
		return null;
	}
}
