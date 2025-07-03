package com.hokhanh.artist.controller;

import org.springframework.stereotype.Controller;

import com.hokhanh.artist.service.ArtistService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ArtistController {
	private final ArtistService service;
	
	
}
