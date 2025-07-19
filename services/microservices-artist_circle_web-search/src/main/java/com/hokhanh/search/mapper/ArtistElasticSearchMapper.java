package com.hokhanh.search.mapper;

import org.springframework.stereotype.Service;

import com.hokhanh.common.rabbitMq.dto.ArtistMessage;
import com.hokhanh.search.model.ArtistElasticSearch;


@Service
public class ArtistElasticSearchMapper {

	public ArtistElasticSearch toArtistElasticSearch(ArtistMessage artist, String id) {
		return ArtistElasticSearch.builder()
				.id(id)
				.artistId(artist.id())
				.artistName(artist.artistName())
				.longitude(artist.longitude())
				.latitude(artist.latitude())
				.build();
	}
}
