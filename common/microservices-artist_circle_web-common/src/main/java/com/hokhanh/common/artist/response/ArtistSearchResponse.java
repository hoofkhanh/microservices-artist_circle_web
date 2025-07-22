package com.hokhanh.common.artist.response;

public record ArtistSearchResponse(
	Long id,
	String artistName,
	String avatarUrl,
	String residence,
	Double distanceInMeters
) {

}
