package com.hokhanh.artist.response.artist.profileUpdate;

import com.hokhanh.artist.response.common.ApiResponse;

public record ArtistProfileUpdateApiResponse(
	ApiResponse apiResponse,
	ArtistProfileUpdateResponse data
) {

}
