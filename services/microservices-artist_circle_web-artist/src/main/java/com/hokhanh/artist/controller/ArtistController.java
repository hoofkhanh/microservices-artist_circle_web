package com.hokhanh.artist.controller;


import java.util.List;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.ContextValue;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.hokhanh.artist.request.artist.ArtistProfileUpdateRequest;
import com.hokhanh.artist.request.artist.ArtistRegistrationRequest;
import com.hokhanh.artist.response.artist.create.ArtistRegistrationApiResponse;
import com.hokhanh.artist.response.artist.search.ArtistSearchDetailApiResponse;
import com.hokhanh.artist.response.artist.update.ArtistProfileUpdateApiResponse;
import com.hokhanh.artist.service.ArtistService;
import com.hokhanh.common.artist.response.ArtistSearchResponse;
import com.hokhanh.common.gpsLocation.response.ArtistGpsLocationResponse;
import com.hokhanh.common.graphQL.HttpHeadersConstants;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ArtistController {
	private final ArtistService service;
	
	@MutationMapping
	public ArtistRegistrationApiResponse registerArtist(@Argument @Valid ArtistRegistrationRequest request) {
		return service.register(request);
	}
	
	@MutationMapping
	public ArtistProfileUpdateApiResponse updateProfile(@Argument @Valid ArtistProfileUpdateRequest request,
			@ContextValue(name = HttpHeadersConstants.HEADER_USER_ID) String userId) {
		return service.update(request, userId);
	}
	
	@QueryMapping
	public ArtistSearchDetailApiResponse searchMe(@ContextValue(name = HttpHeadersConstants.HEADER_USER_ID) String userId) {
		return service.findByUserId(userId);
	}
	
	@QueryMapping
	public ArtistSearchDetailApiResponse searchArtist(@Argument @NotNull(message = "artist is mandatory") Long artistId,
			@ContextValue(name = HttpHeadersConstants.HEADER_USER_ID) String userId
	) {
		return service.findById(artistId, userId);
	}
	
	@QueryMapping
	public List<ArtistSearchResponse> searchArtistsInternal(@Argument @NotNull(message = "ids is mandatory") List<Long> ids) {
		return service.searchArtists(ids);
	}
	
	@QueryMapping
	public ArtistGpsLocationResponse getMyGpsLocationAndArtistIdInternal(@Argument @NotNull(message = "userId is mandatory") Long userId) {
		return service.getMyGpsLocationAndArtistId(userId);
	}
	
}
