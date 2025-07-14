package com.hokhanh.artist.response.gpsLocation.common;

import java.time.LocalDateTime;

public record GpsLocationResponse(
	Long id,
	Long artistId,
	float longitude,
	float latitude,
	LocalDateTime updatedAt
) {

}
