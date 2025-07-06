package com.hokhanh.artist.controller;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
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
	
	// tạo hàm thêm ảnh, nhạc vào cloudinary
	
	@MutationMapping
	public ArtistApiResponse registerArtist(@Argument @Valid ArtistRequest artistRequest) {
		return service.register(artistRequest);
	}
}
