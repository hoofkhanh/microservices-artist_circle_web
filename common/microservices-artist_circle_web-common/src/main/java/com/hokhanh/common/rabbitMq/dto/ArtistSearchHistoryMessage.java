package com.hokhanh.common.rabbitMq.dto;

public record ArtistSearchHistoryMessage(
	Long searcherArtistId,
	Long targetArtistId
) {

}
