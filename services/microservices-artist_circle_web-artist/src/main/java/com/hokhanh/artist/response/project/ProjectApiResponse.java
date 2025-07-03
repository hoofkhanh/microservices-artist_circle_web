package com.hokhanh.artist.response.project;

import com.hokhanh.artist.response.common.StatusType;

public record ProjectApiResponse(
	boolean success,
	String message,		
	StatusType statusType,
	ProjectResponse data
) {

}
