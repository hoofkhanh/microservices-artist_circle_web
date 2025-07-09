package com.hokhanh.common.cloudinary.dto;

public record CloudinaryUploadApiResponse(
	boolean success,
	String message,
	CloudinaryUploadResponse data
) {

}
