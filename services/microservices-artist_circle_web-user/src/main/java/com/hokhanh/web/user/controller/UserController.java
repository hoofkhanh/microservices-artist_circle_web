package com.hokhanh.web.user.controller;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

import com.hokhanh.web.user.request.UserRequest;
import com.hokhanh.web.user.response.UserApiResponse;
import com.hokhanh.web.user.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class UserController {
	private final UserService service;
	
	@MutationMapping
	public UserApiResponse register(@Argument @Valid UserRequest userRequest) {
		return service.register(userRequest);
	}
	
	@MutationMapping
	public UserApiResponse verifyRegistrationOtp(@Argument String otp, @Argument String email) {
		return service.verifyRegistrationOtp(otp, email);
	}
	
	@MutationMapping
	public UserApiResponse resendOtp(@Argument String email) {
		return service.resendOtp(email);
	}
}
