package com.hokhanh.artist.response.artist.update;

import com.hokhanh.artist.response.artist.common.ArtistResponse;

public record ArtistProfileUpdateResponse(
	ArtistResponse artist,
	String avatarUrl,
	String instagramUrl,
	String facebookUrl,
	String tiktokUrl,
	String description
) {

}
