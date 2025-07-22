package com.hokhanh.search.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.hokhanh.search.model.ArtistSearch;


public interface ArtistSearchRepository extends ElasticsearchRepository<ArtistSearch, String> {
	ArtistSearch findByArtistId(Long artistId);

	Page<ArtistSearch> findByArtistNameContainingIgnoreCase(String artistName, Pageable pageable);
	
}
