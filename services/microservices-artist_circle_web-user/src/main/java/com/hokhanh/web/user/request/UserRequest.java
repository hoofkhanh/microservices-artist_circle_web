package com.hokhanh.web.user.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserRequest(
	@NotNull(message = "roleId is mandatory")
	Long roleId,
	
	@NotBlank(message = "fullName is mandatory")
	String fullName,
	
	@NotBlank(message = "email is mandatory")
	@Email(message = "email must be created from emmail form")
	String email,
	
	@NotBlank(message = "password is mandatory")
	String password
) {
	
}
