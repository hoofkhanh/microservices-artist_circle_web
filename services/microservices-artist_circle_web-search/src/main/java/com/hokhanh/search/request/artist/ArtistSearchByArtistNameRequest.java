package com.hokhanh.search.request.artist;



import com.hokhanh.search.request.page.PageCustomRequest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ArtistSearchByArtistNameRequest(
	@NotBlank(message = "artistName is mandatory")
	String artistName,
	
	@Valid
	@NotNull(message = "page must not be null")
	PageCustomRequest page
) {
	
}
