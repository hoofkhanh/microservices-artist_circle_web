package com.hokhanh.common.cloudinary.dto;

import jakarta.validation.constraints.NotBlank;

public record CloudinaryUploadRequest(
	@NotBlank(message = "publicId is mandatory")
	String publicId,
	
	@NotBlank(message = "secureUrl is mandatory")
	String secureUrl
) {

}
