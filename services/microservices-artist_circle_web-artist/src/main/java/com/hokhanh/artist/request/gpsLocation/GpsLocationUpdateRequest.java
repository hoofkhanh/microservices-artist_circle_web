package com.hokhanh.artist.request.gpsLocation;


import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record GpsLocationUpdateRequest(
	@NotNull(message = "project must not be null")
	@Valid
	GpsLocationRequest gpsLocation
	
//	k cần vì có userId trong token => lấy artist => lấy id của gps
//	@NotNull(message = "id must not be null")
//	@Positive(message = "id must be a positive number")
//	Long id
) {

}
