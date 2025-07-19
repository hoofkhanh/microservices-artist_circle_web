package com.hokhanh.search.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.hokhanh.search.model.ArtistElasticSearch;

public interface ArtistElasticsearchRepository extends ElasticsearchRepository<ArtistElasticSearch, String> {
	ArtistElasticSearch findByArtistId(Long artistId);

}
