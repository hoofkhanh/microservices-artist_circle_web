package com.hokhanh.artist.response.project.create;

import com.hokhanh.artist.response.common.ApiResponse;

public record ProjectCreationApiResponse(
	ApiResponse apiResponse,
	ProjectCreationResponse data
) {
	
}
