package com.hokhanh.artist.request.artist;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ArtistProfileUpdateRequest(
	@Valid
	@NotNull(message = "artist must not be null")
	ArtistRequest artist,
	
	@NotNull(message = "id must be not null")
	@Positive(message = "id must be a positive number")
	Long id,
	
	@NotBlank(message = "avatarUrl is mandatory")
	String avatarUrl,
	@NotBlank(message = "avatarCloudinaryPublicId is mandatory")
	String avatarCloudinaryPublicId,
	
	String instagramUrl,
	String facebookUrl,
	String tiktokUrl,
	String description
) {
	
}
