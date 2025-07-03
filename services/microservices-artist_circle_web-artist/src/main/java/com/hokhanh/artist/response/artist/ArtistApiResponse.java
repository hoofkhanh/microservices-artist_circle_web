package com.hokhanh.artist.response.artist;

import com.hokhanh.artist.response.common.StatusType;

public record ArtistApiResponse(
	boolean success,
	String message,
	StatusType statusType,
	ArtistResponse data
) {

}
