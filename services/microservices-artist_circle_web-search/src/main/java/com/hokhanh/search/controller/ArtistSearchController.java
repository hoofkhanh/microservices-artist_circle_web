package com.hokhanh.search.controller;

import java.io.IOException;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.ContextValue;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.hokhanh.common.graphQL.HttpHeadersConstants;
import com.hokhanh.search.request.artist.ArtistSearchByArtistNameRequest;
import com.hokhanh.search.request.artist.ArtistSearchNearbyRequest;
import com.hokhanh.search.response.artist.ArtistSearchPageResponse;
import com.hokhanh.search.service.ArtistSearchService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ArtistSearchController {
	private final ArtistSearchService service;

	@QueryMapping
	public ArtistSearchPageResponse searchArtistsByName(@Argument @Valid ArtistSearchByArtistNameRequest request) {
		return service.searchArtistsByName(request);
	}
	
	@QueryMapping
	public ArtistSearchPageResponse searchArtistsNearBy(@Argument @Valid ArtistSearchNearbyRequest request,
			@ContextValue(name = HttpHeadersConstants.HEADER_USER_ID) String userId
	) throws IOException {
		return service.searchArtistsNearBy(request, userId);
	}
}
