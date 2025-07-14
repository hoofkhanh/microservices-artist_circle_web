package com.hokhanh.artist.response.project.common;

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
	String musicCloudinaryPublicId,
	String imageUrl,
	String imageCloudinaryPublicId,
	float duration,
	String otherMusicGenreNames
) {

}
