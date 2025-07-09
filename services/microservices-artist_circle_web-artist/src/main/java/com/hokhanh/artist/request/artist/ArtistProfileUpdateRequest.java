package com.hokhanh.artist.request.artist;

import com.hokhanh.common.cloudinary.dto.CloudinaryUploadRequest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ArtistProfileUpdateRequest(
	@Valid
	@NotNull(message = "artist must not be null")
	ArtistRequest artist,
	
	@NotNull(message = "id must be not null")
	@Positive(message = "id must be a positive number")
	Long id,
	
	@Valid
	CloudinaryUploadRequest avatarUpload,
	
	String instagramUrl,
	String facebookUrl,
	String tiktokUrl,
	String description
) {
	
}
