package com.hokhanh.search.request.artist;


import com.hokhanh.search.request.page.PageCustomRequest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record ArtistSearchNearbyRequest(
	@Valid
	@NotNull(message = "page must not be null")
	PageCustomRequest page
) {

}
