package com.hokhanh.search.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(indexName = "artists")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArtistElasticSearch {
	@Id
	private String id;
	private Long artistId;
	private String artistName;
	private Float longitude;
	private Float latitude;
}
