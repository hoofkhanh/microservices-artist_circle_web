package com.hokhanh.search.request.page;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record PageCustomRequest(
		
	@NotNull(message = "pageNumber is mandatory")
	@Positive(message = "pageNumber must be a positive number")
	Integer pageNumber,
	
	@NotNull(message = "pageSize is mandatory")
	@Positive(message = "pageSize must be a positive number")
	@Max(value = 10, message = "pageSize must not exceed 10")
	Integer pageSize 
) {
	
}
