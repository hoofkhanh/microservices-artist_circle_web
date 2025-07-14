package com.hokhanh.artist.request.project;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ProjectUpdateRequest(
	@NotNull(message = "project must not be null")
	@Valid
	ProjectRequest project,
	
	@NotNull(message = "id must not be null")
	@Positive(message = "id must be a positive number")
	Long id
) {

}
