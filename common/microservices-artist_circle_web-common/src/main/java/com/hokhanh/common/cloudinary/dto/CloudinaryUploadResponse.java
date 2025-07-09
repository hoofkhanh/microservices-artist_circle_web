package com.hokhanh.common.cloudinary.dto;

public record CloudinaryUploadResponse(
	String folder, 
	boolean overwrite, 
	String publicId, 
	String resourceType,
	long timestamp, 
	String apiKey,
	String signature
) {

}
