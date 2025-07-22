package com.hokhanh.search.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.hokhanh.common.artist.response.ArtistSearchResponse;
import com.hokhanh.common.gpsLocation.response.ArtistGpsLocationResponse;
import com.hokhanh.search.client.ArtistClient;
import com.hokhanh.search.mapper.ArtistSearchMapper;
import com.hokhanh.search.model.ArtistSearch;
import com.hokhanh.search.repository.ArtistSearchRepository;
import com.hokhanh.search.request.artist.ArtistSearchByArtistNameRequest;
import com.hokhanh.search.request.artist.ArtistSearchNearbyRequest;
import com.hokhanh.search.request.page.PageCustomRequest;
import com.hokhanh.search.response.artist.ArtistSearchPageResponse;
import com.hokhanh.search.response.artist.ArtistSearchWithDistance;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.GeoLocation;
import co.elastic.clients.elasticsearch._types.LatLonGeoLocation;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.SourceConfig;
import co.elastic.clients.json.JsonData;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ArtistSearchService {
	private final ArtistSearchRepository artistRepository;
	private final ArtistSearchMapper artistMapper;
	private final ArtistClient artistClient;
	private final ElasticsearchClient elasticsearchClient;

	public ArtistSearchPageResponse searchArtistsByName(ArtistSearchByArtistNameRequest request) {
		Pageable pageable = getPageable(request.page());
		Page<ArtistSearch> artists = artistRepository.findByArtistNameContainingIgnoreCase(request.artistName(), pageable);
		List<Long> ids = artists.map( artist -> artist.getArtistId() ).toList();
		return buildArtistPageResponse(ids, null, artists.getPageable(), artists.getTotalElements());
	}
	
	public ArtistSearchPageResponse searchArtistsNearBy(ArtistSearchNearbyRequest request, String userId) throws IOException {
		ArtistGpsLocationResponse location =  artistClient.getMyGpsLocationAndArtistIdInternal(Long.parseLong(userId));
		Pageable pageable = getPageable(request.page());
		Page<ArtistSearchWithDistance> artists = searchNearby(location.latitude(), location.longitude(), 10.00, location.artistId(),  pageable);
		Map<Long, Double> distanceMap = artists.getContent().stream()
		    .collect(Collectors.toMap(
		        a -> a.artist().getArtistId(),
		        a -> a.distanceInMeters()
		    ));
		List<Long> ids = artists.map( artist -> artist.artist().getArtistId() ).toList();
		return buildArtistPageResponse(ids, distanceMap, artists.getPageable(), artists.getTotalElements());
	}
	
	private ArtistSearchPageResponse buildArtistPageResponse(List<Long> ids, Map<Long, Double> distanceMap, Pageable pageable, Long totalElements) {
	    List<ArtistSearchResponse> artists = artistClient.searchArtistsInternal(ids);
	    if(distanceMap != null) {
	    	artists  = artists.stream()
    	        .map(response -> new ArtistSearchResponse(
    	            response.id(),
    	            response.artistName(),
    	            response.avatarUrl(),
    	            response.residence(),
    	            distanceMap.getOrDefault(response.id(), null) 
    	        ))
    	        .toList();
	    }
	    
	    return artistMapper.toArtistPageResponse(artists, pageable, totalElements);
	} 
	
	private Pageable getPageable(PageCustomRequest page) {
		Integer pageNumber = page.pageNumber() -1;
		Pageable pageable = PageRequest.of(pageNumber, page.pageSize());
		return pageable;
	}
	
	private Page<ArtistSearchWithDistance> searchNearby(double lat, double lon, double distanceKm, Long artistId, Pageable pageCustom) throws IOException {
		String indexName = "artists";

	    SearchResponse<ArtistSearch> response = elasticsearchClient.search(s -> s
	        .index(indexName)
	        .from(pageCustom.getPageNumber() * pageCustom.getPageSize())
	        .size(pageCustom.getPageSize())
	        .source(SourceConfig.of(so -> so.fetch(true)))
	        .query(q -> q
	            .bool(b -> b
	                .filter(f -> f
	                    .geoDistance(g -> g
	                        .field("location")
	                        .distance(distanceKm + "km")
	                        .location(GeoLocation.of(gl -> gl.latlon(LatLonGeoLocation.of(builder -> builder.lat(lat).lon(lon)))))
	                    )
	                )
	                .mustNot(mn -> mn
                        .term(t -> t
                            .field("artistId")
                            .value(artistId)
                        )
                    )
	            )
	        )
	        .sort(sortBuilder  -> sortBuilder 
	            .geoDistance(g -> g
	                .field("location")
	                .location(GeoLocation.of(gl -> gl.latlon(LatLonGeoLocation.of(b -> b.lat(lat).lon(lon)))))
	                .order(SortOrder.Asc)
	            ))
	       .scriptFields("distance", sf -> sf
		        .script(ss -> ss
		                .source("doc['location'].arcDistance(params.lat, params.lon)")
		                .params(Map.of(
		                    "lat", JsonData.of(lat),
		                    "lon", JsonData.of(lon)
		                ))
		            )
	        )
	        ,ArtistSearch.class
	    );

	    System.out.println(response.hits().total());
	    long totalElements = response.hits().total() != null ? response.hits().total().value() : 0;
	    List<ArtistSearchWithDistance> content = response.hits().hits().stream()
	    	    .map(hit -> {
	    	    	System.out.println("Raw source: " + hit.source());
	    	        ArtistSearch artist = hit.source();
	    	        double distance = hit.sort() != null && !hit.sort().isEmpty()
	    	        	    ? hit.sort().get(0).doubleValue()
	    	        	    : 0;
	    	        return new ArtistSearchWithDistance(artist, distance);
	    	    })
	    	    .toList();

	    for (ArtistSearchWithDistance artistSearchWithDistance : content) {
			System.out.println(artistSearchWithDistance.distanceInMeters());
			System.out.println(artistSearchWithDistance.artist());
		}
	    Pageable pageable = PageRequest.of(pageCustom.getPageNumber(), pageCustom.getPageSize());
	    return new PageImpl<>(content, pageable, totalElements);
	}





}
