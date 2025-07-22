package com.hokhanh.search.response.common;

public record ApiResponse(
	boolean success,
	String message,
	StatusType statusType
) {

}
