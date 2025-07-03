package com.hokhanh.artist.response.gpsLocation;

import java.time.LocalDateTime;

public record GpsLocationResponse(
	Long id,
	float longitude,
	float latitude,
	LocalDateTime updatedAt
) {

}
