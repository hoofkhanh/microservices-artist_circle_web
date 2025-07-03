package com.hokhanh.artist.response.project;

import java.util.List;

import com.hokhanh.artist.response.common.MusicGenreResponse;

public record ProjectResponse(
	Long id,
	ArtistSummaryResponse poster,
	List<ArtistSummaryResponse> collaborators,
	List<MusicGenreResponse> musicGenres,
	String customCollaborators,
	String name,
	String description,
	String musicUrl,
	String imageUrl,
	float duration,
	String otherMusicGenreNames
) {

}
