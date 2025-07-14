package com.hokhanh.artist.response.gpsLocation.update;

import com.hokhanh.artist.response.common.ApiResponse;

public record GpsLocationUpdateApiResponse(
	ApiResponse apiResponse,
	GpsLocationUpdateResponse data
) {

}
