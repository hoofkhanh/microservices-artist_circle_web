package com.hokhanh.web.user.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record AuthenticationRequest(
	@NotBlank(message = "email is mandatory")
	@Email(message = "email must be created from emmail form")
	String email, 
	
	@NotBlank(message = "password is mandatory")
	String password
) {

}
