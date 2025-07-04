package com.hokhanh.artist.controller;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.stereotype.Controller;

import com.hokhanh.artist.request.ArtistRequest;
import com.hokhanh.artist.response.artist.ArtistApiResponse;
import com.hokhanh.artist.service.ArtistService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ArtistController {
	private final ArtistService service;
	
//	public ArtistApiResponse registerOrUpdateArtistProfile(@Argument @Valid ArtistRequest request) {
//		
//	}
}
