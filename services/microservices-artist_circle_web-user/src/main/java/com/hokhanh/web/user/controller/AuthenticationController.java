package com.hokhanh.web.user.controller;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.ContextValue;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.hokhanh.web.user.constant.AuthenticationConstants;
import com.hokhanh.web.user.request.AuthenticationRequest;
import com.hokhanh.web.user.response.AuthenticationApiResponse;
import com.hokhanh.web.user.response.AuthenticationStatusType;
import com.hokhanh.web.user.service.AuthenticationService;

import graphql.GraphQLContext;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class AuthenticationController {
	private final AuthenticationService authService;

	@MutationMapping
	public AuthenticationApiResponse login(@Argument @Valid AuthenticationRequest request, GraphQLContext context) {
		return authService.login(request, context);
	}

	@MutationMapping
	public AuthenticationApiResponse logout(
			@ContextValue(name = AuthenticationConstants.REFRESH_TOKEN_COOKIE_NAME) String refreshToken,
			@ContextValue(name = AuthenticationConstants.AUTHORIZATION_CONTEXT_KEY) String authorization,
			GraphQLContext context) {
		if(refreshToken == null || refreshToken.isBlank() || authorization == null || authorization.isBlank()) {
			return new AuthenticationApiResponse(false, "Missing refreshToken or authorization", 
					AuthenticationStatusType.MISSING_FIELD, null);
		}
		String token = authorization.replace("Bearer ", "").trim();
		return authService.logout(token, refreshToken, context);
	}

	
	// this function is internal not publlic or private
	@QueryMapping
	public boolean isTokenBlockedInternal(@Argument String accessToken) {
		return authService.isTokenBlocked(accessToken);
	}

	@MutationMapping
	public AuthenticationApiResponse refreshToken(
			@ContextValue(name = AuthenticationConstants.REFRESH_TOKEN_COOKIE_NAME, required = false) 
			String refreshToken) {
		return authService.refreshToken(refreshToken);
	}
}
