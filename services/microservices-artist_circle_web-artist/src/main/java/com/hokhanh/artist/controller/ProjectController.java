package com.hokhanh.artist.controller;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.ContextValue;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

import com.hokhanh.artist.request.project.ProjectCreationRequest;
import com.hokhanh.artist.request.project.ProjectUpdateRequest;
import com.hokhanh.artist.response.project.create.ProjectCreationApiResponse;
import com.hokhanh.artist.response.project.update.ProjectUpdateApiResponse;
import com.hokhanh.artist.service.ProjectService;
import com.hokhanh.common.graphQL.HttpHeadersConstants;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ProjectController {

	private final ProjectService service;
	
	@MutationMapping
	public ProjectCreationApiResponse createProject(@Argument @Valid ProjectCreationRequest request,
		@ContextValue(name = HttpHeadersConstants.HEADER_USER_ID) String userId
	) {
		return service.createProject(request, userId);
	}
	
	@MutationMapping
	public ProjectUpdateApiResponse updateProject(@Valid @Argument ProjectUpdateRequest request,
		@ContextValue(name = HttpHeadersConstants.HEADER_USER_ID) String userId
	) {
		return service.updateProject(request, userId);
	} 
}
