package com.hokhanh.artist.response.project;

import com.hokhanh.artist.response.common.StatusType;

public record ProjectListApiResponse(
	boolean success,
	String message,
	StatusType statusType,
	ProjectPage data
) {

}
