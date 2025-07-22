package com.hokhanh.search.mapper;

import org.springframework.stereotype.Service;

import com.hokhanh.common.rabbitMq.dto.ArtistSearchHistoryMessage;
import com.hokhanh.search.model.ArtistSearchHistory;

@Service
public class ArtistSearchHistoryMapper {

	public ArtistSearchHistory toArtistSearchHistory(ArtistSearchHistoryMessage artist) {
		return ArtistSearchHistory.builder()
				.searcherArtistId(artist.searcherArtistId())
				.targetArtistId(artist.targetArtistId())
				.build();
	}
}
