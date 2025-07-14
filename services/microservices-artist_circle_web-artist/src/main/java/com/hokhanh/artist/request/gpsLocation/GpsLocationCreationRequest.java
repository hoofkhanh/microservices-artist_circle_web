package com.hokhanh.artist.request.gpsLocation;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record GpsLocationCreationRequest(
	@NotNull(message = "gpsLocation must not be null")
	@Valid
	GpsLocationRequest gpsLocation
) {

}
