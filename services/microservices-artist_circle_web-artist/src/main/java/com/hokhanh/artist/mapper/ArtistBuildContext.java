package com.hokhanh.artist.mapper;

public record ArtistBuildContext(
    Long userId,
    Long id,
	String avatarUrl,
	String avatarCloudinaryPublicId,
	String instagramUrl,
	String facebookUrl,
	String tiktokUrl,
	String description
) {}