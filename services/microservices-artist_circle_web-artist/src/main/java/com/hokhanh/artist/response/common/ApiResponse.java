package com.hokhanh.artist.response.common;

public record ApiResponse(
	boolean success,
	String message,
	StatusType statusType
) {

}
