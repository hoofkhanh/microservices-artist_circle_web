package com.hokhanh.artist.request.gpsLocation;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

public record GpsLocationRequest(
	@NotNull(message = "longitude is mandatory")
	@DecimalMin(value = "-180", inclusive = true, message = "Longitude must be ≥ -180.0")
	@DecimalMax(value = "180", inclusive = true, message = "Longitude must be ≤ 180.0")
	Float longitude,
	
	@NotNull(message = "latitude is mandatory")
	@DecimalMin(value = "-90", inclusive = true, message = "Latitude must be ≥ -90.0")
	@DecimalMax(value = "90", inclusive = true, message = "Latitude must be ≤ 90.0")
	Float latitude
) {

}
