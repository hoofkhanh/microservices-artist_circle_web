package com.hokhanh.common.cloudinary.dto;


public record CloudinaryApiResponse(
	boolean success,
	String message,
	CloudinaryResponse data
) {

}
