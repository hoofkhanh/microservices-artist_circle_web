package com.hokhanh.common.rabbitMq.dto;

public record ArtistMessage(
	Long id,
	String artistName,
	Float longitude,
	Float latitude
) {

}
