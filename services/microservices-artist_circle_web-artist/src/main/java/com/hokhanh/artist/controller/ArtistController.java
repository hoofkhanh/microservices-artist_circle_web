package com.hokhanh.artist.controller;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.ContextValue;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

import com.hokhanh.artist.request.artist.ArtistProfileUpdateRequest;
import com.hokhanh.artist.request.artist.ArtistRegistrationRequest;
import com.hokhanh.artist.response.artist.create.ArtistRegistrationApiResponse;
import com.hokhanh.artist.response.artist.update.ArtistProfileUpdateApiResponse;
import com.hokhanh.artist.service.ArtistService;
import com.hokhanh.common.graphQL.HttpHeadersConstants;

import jakarta.validation.Valid;
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
}
