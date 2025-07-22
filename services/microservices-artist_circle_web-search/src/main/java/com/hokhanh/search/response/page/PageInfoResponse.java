package com.hokhanh.search.response.page;

public record PageInfoResponse(
	int size,
	int number,
	Long totalElements,
	int totalPages
) {

}
