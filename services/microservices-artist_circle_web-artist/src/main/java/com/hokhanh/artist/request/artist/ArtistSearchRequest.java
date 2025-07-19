package com.hokhanh.artist.request.artist;

import com.hokhanh.artist.request.gpsLocation.GpsLocationRequest;

import jakarta.validation.Valid;

public record ArtistSearchRequest(
	String artistName,
	
	@Valid
	GpsLocationRequest gpsLocation
) {

}
