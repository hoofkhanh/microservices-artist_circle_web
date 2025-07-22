package com.hokhanh.search.repository;


import org.springframework.data.mongodb.repository.MongoRepository;

import com.hokhanh.search.model.ArtistSearchHistory;

public interface ArtistSearchHistoryRepository extends MongoRepository<ArtistSearchHistory, String> {
	ArtistSearchHistory findBySearcherArtistIdAndTargetArtistId(
		Long searcherArtistId, Long targetArtistId
	);
	
	int deleteBySearcherArtistId(Long searcherArtistId);
}
