package com.hokhanh.search.mapper;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;
import org.springframework.stereotype.Service;

import com.hokhanh.common.artist.response.ArtistSearchResponse;
import com.hokhanh.common.rabbitMq.dto.ArtistMessage;
import com.hokhanh.search.model.ArtistSearch;
import com.hokhanh.search.response.artist.ArtistSearchPageResponse;
import com.hokhanh.search.response.page.PageInfoResponse;


@Service
public class ArtistSearchMapper {

	public ArtistSearch toArtistSearch(ArtistMessage artist) {
		return ArtistSearch.builder()
				.artistId(artist.id())
				.artistName(artist.artistName())
				.location(new GeoPoint(artist.latitude(), artist.longitude()))
				.build();
	}
	
	public ArtistSearchPageResponse toArtistPageResponse(List<ArtistSearchResponse> artistResponseList,
			Pageable pageable, Long totalElements) {
		int totalPages = (int) Math.ceil((double) totalElements / pageable.getPageSize());
	    PageInfoResponse pageInfo = new PageInfoResponse(
	    		pageable.getPageSize(),
	    		pageable.getPageNumber() + 1,
	    		totalElements,
	    		totalPages
	    );

	    return new ArtistSearchPageResponse(artistResponseList, pageInfo);

	}
	
	
}
