package com.hokhanh.artist.response.gpsLocation;

import com.hokhanh.artist.response.common.StatusType;

public record GpsLocationApiResponse(
	boolean success,
	String message,		
	StatusType statusType,
	GpsLocationResponse data
) {

}
