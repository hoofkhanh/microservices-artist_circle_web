package com.hokhanh.artist.response.artist.search;


import java.util.List;

import com.hokhanh.artist.response.artist.common.ArtistResponse;
import com.hokhanh.artist.response.gpsLocation.common.GpsLocationResponse;
import com.hokhanh.artist.response.project.common.ProjectResponse;

public record ArtistSearchDetailResponse(
	ArtistResponse artist,
	String avatarUrl,
	String avatarCloudinaryPublicId, // có thê null nếu k phải tôi truy cập
	String instagramUrl,
	String facebookUrl,
	String tiktokUrl,
	String description,
	List<ProjectResponse> projects,
	GpsLocationResponse gpsLocation
) {

}
