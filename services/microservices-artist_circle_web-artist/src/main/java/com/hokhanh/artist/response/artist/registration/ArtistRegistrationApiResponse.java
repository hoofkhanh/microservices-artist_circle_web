package com.hokhanh.artist.response.artist.registration;

import com.hokhanh.artist.response.common.ApiResponse;

public record ArtistRegistrationApiResponse(
	ApiResponse apiResponse,
	ArtistRegistrationResponse data
) {

}
