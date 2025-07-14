package com.hokhanh.artist.controller;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.ContextValue;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

import com.hokhanh.artist.request.gpsLocation.GpsLocationCreationRequest;
import com.hokhanh.artist.request.gpsLocation.GpsLocationUpdateRequest;
import com.hokhanh.artist.response.gpsLocation.create.GpsLocationCreationApiResponse;
import com.hokhanh.artist.response.gpsLocation.update.GpsLocationUpdateApiResponse;
import com.hokhanh.artist.service.GpsLocationService;
import com.hokhanh.common.graphQL.HttpHeadersConstants;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class GpsLocationController {
	private final GpsLocationService service;
	
	@MutationMapping
	public GpsLocationCreationApiResponse createGpsLocaiton(@Argument @Valid GpsLocationCreationRequest request,
		@ContextValue(name = HttpHeadersConstants.HEADER_USER_ID) String userId
	) {
		return service.createGpsLocation(request,userId);
	}
	
	@MutationMapping
	public GpsLocationUpdateApiResponse updateGpsLocation(@Valid @Argument GpsLocationUpdateRequest request,
		@ContextValue(name = HttpHeadersConstants.HEADER_USER_ID) String userId
	) {
		return service.updateGpsLocation(request,userId);
	}
}
