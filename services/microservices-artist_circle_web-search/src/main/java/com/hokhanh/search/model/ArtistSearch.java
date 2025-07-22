package com.hokhanh.search.model;

import org.springframework.data.annotation.Id;

import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.GeoPointField;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(indexName = "artists")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class ArtistSearch{
	@Id
	private String id;
	private Long artistId;
	private String artistName;
//	private Float longitude;
//	private Float latitude;
	
	@GeoPointField
    private GeoPoint location;
}
