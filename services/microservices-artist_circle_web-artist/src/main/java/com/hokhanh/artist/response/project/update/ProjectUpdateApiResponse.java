package com.hokhanh.artist.response.project.update;

import com.hokhanh.artist.response.common.ApiResponse;

public record ProjectUpdateApiResponse(
	ApiResponse apiResponse,
	ProjectUpdateResponse data
) {
	
}
