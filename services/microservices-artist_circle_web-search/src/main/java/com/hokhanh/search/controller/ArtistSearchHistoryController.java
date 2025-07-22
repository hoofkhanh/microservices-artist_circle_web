package com.hokhanh.search.controller;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.ContextValue;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

import com.hokhanh.common.graphQL.HttpHeadersConstants;
import com.hokhanh.search.response.common.ApiResponse;
import com.hokhanh.search.service.ArtistSearchHistoryService;

import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ArtistSearchHistoryController {
	private final ArtistSearchHistoryService service;

	@MutationMapping
	public ApiResponse deleteArtistSearchHistory(@Argument @NotBlank(message = "id must not be blank") String id,
			@ContextValue(name = HttpHeadersConstants.HEADER_USER_ID) String userId) {
		return service.deleteArtistSearchHistory(id, userId);
	}
	
	@MutationMapping
	public ApiResponse deleteAllArtistSearchHistories(@ContextValue(name = HttpHeadersConstants.HEADER_USER_ID) String userId) {
		return service.deleteAllArtistSearchHistories(userId);
	}
}
