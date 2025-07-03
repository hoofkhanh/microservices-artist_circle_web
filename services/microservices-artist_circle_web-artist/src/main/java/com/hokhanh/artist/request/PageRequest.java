package com.hokhanh.artist.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record PageRequest(
		
	@NotNull(message = "page is mandatory")
	@Positive(message = "page must be a positive number")
	Long page,
	
	@NotNull(message = "size is mandatory")
	@Positive(message = "size must be a positive number")
	Long size,
	
	String sortBy,
	Boolean ascending
) {

}
