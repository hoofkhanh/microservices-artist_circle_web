package com.hokhanh.artist.response.artist.search;

import com.hokhanh.artist.response.common.ApiResponse;

public record ArtistSearchApiResponse(
	ApiResponse apiResponse,
	ArtistSearchResponse data
) {

}
