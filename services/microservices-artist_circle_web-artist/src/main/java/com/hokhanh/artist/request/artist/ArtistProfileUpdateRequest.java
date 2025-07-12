package com.hokhanh.artist.request.artist;

import com.hokhanh.common.cloudinary.dto.CloudinaryUploadRequest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record ArtistProfileUpdateRequest(
	@Valid
	@NotNull(message = "artist must not be null")
	ArtistRequest artist,
	
	@Valid
	CloudinaryUploadRequest avatarUpload,
	
	String instagramUrl,
	String facebookUrl,
	String tiktokUrl,
	String description
) {
	
}
