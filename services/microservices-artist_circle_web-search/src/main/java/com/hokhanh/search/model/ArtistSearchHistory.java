package com.hokhanh.search.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "artist_search_history")
public class ArtistSearchHistory {
	@Id
	private String id;
	private Long searcherArtistId;
	private Long targetArtistId;
	
	@LastModifiedDate
	private LocalDateTime updatedAt;
}
