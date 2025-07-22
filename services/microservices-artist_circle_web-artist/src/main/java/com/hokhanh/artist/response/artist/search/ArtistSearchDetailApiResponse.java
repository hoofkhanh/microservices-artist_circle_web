package com.hokhanh.artist.response.artist.search;

import com.hokhanh.artist.response.common.ApiResponse;

public record ArtistSearchDetailApiResponse(
	ApiResponse apiResponse,
	ArtistSearchDetailResponse data
) {

}
