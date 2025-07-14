package com.hokhanh.artist.response.artist.create;

import com.hokhanh.artist.response.common.ApiResponse;

public record ArtistRegistrationApiResponse(
	ApiResponse apiResponse,
	ArtistRegistrationResponse data
) {

}
