package com.hokhanh.artist.response.gpsLocation.create;

import com.hokhanh.artist.response.common.ApiResponse;

public record GpsLocationCreationApiResponse(
	ApiResponse apiResponse,
	GpsLocationCreationResponse data
) {

}
