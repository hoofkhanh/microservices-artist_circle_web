package com.hokhanh.artist.request.artist;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ArtistRegistrationRequest(
	@Valid
	@NotNull(message = "artist must not be null")
	ArtistRequest artist,
	
	@NotNull(message = "userId must not be null")
	@Positive(message = "userId must be a positive number")
	Long userId
) {

}
