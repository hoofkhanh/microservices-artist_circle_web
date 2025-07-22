package com.hokhanh.artist.response.artist.common;

import java.time.LocalDate;
import java.util.List;

import com.hokhanh.artist.response.common.MusicGenreResponse;



public record ArtistResponse(
	Long id,
	List<RoleResponse> roles,
	List<MusicGenreResponse> musicGenres,
	LocalDate birthdate,
	boolean gender,
	String artistName,
	String residence,
	String otherRoleNames,
	String otherMusicGenreNames
) {

}
