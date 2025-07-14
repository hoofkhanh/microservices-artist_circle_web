package com.hokhanh.artist.request.project;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record ProjectCreationRequest(
	@NotNull(message = "project must not be null")
	@Valid
	ProjectRequest project
) {

}
