package com.hokhanh.artist.controller;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

import com.hokhanh.artist.request.artist.ArtistRegistrationRequest;
import com.hokhanh.artist.response.artist.registration.ArtistRegistrationApiResponse;
import com.hokhanh.artist.service.ArtistService;

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
}
