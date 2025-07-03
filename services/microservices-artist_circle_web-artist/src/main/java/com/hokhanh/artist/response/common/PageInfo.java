package com.hokhanh.artist.response.common;

public record PageInfo(
	Long size,
	Long number,
	Long totalElements,
	Long totalPages
) {

}
