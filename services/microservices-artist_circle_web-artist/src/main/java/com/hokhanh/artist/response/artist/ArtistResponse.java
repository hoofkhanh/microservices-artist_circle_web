package com.hokhanh.artist.response.artist;

import java.time.LocalDate;
import java.util.List;

import com.hokhanh.artist.response.common.MusicGenreResponse;
import com.hokhanh.artist.response.gpsLocation.GpsLocationResponse;
import com.hokhanh.artist.response.project.ProjectPage;

public record ArtistResponse(
	Long id,
	List<RoleResponse> roles,
	List<MusicGenreResponse> musicGenres,
	ProjectPage projects,
	GpsLocationResponse gpsLocation,
	Long userId,
	LocalDate birthdate,
	boolean gender,
	String avatarUrl,
	String artistName,
	String instagramUrl,
	String facebookUrl,
	String tiktokUrl,
	String description,
	String residence,
	String otherRoleNames,
	String otherMusicGenreNames
) {

}
