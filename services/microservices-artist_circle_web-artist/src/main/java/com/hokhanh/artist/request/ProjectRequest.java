package com.hokhanh.artist.request;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ProjectRequest(
	@NotEmpty(message = "artistCollaborationIds must not be null and empty")
	@Valid
	List<
		@NotNull(message = "Each artistCollaborationId must not be null")
		@Positive(message = "Each artistCollaborationId must be a positive number")
	Long> artistCollaborationIds,
	
	@NotEmpty(message = "musicGenreIds must not be null and empty")
	@Valid
	List<
		@NotNull(message = "Each musicGenreId must not be null")
		@Positive(message = "Each musicGenreId must be a positive number")
	Long> musicGenreIds,
	
	
	String handwrittenCollaborations,
	
	@NotBlank(message = "name is mandatory")
	String name,
	
	@NotBlank(message = "description is mandatory")
	String description,
	
	@NotBlank(message = "musicUrl is mandatory")
	String musicUrl,
	
	@NotBlank(message = "imageUrl is mandatory")
	String imageUrl,
	
	@NotNull(message = "duration must not be null")
	@Positive(message = "duration must be a positive number")
	Float duration,
	
	String otherMusicGenres
) {

}
