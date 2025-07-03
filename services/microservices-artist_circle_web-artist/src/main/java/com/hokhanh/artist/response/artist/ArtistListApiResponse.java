package com.hokhanh.artist.response.artist;

import com.hokhanh.artist.response.common.StatusType;

public record ArtistListApiResponse(
	boolean success,
	String message,
	StatusType statusType,
	ArtistPage data
) {

}
