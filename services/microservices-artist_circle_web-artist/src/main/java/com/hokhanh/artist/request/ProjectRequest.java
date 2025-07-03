package com.hokhanh.artist.request;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ProjectRequest(
	@NotNull(message = "posterId must not be null")
	@Positive(message = "posterId must be a positive number")
	Long posterId,	
	
	// k có @Empty vì collaboratorIds đc null
	@Valid
	List<
		@NotNull(message = "Each collaboratorId must not be null")
		@Positive(message = "Each collaboratorId must be a positive number")
	Long> collaboratorIds,
	
	// k có @Empty vì musicGenreIds đc null
	@Valid
	List<
		@NotNull(message = "Each musicGenreId must not be null")
		@Positive(message = "Each musicGenreId must be a positive number")
	Long> musicGenreIds,
	
	
	String customCollaborators,
	
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
	
	String otherMusicGenreNames
) {

}
