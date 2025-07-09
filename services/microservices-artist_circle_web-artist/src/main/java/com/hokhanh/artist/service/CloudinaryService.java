package com.hokhanh.artist.service;


import org.springframework.stereotype.Service;

import com.hokhanh.common.cloudinary.dto.CloudinaryUploadApiResponse;
import com.hokhanh.common.cloudinary.dto.CloudinaryUploadResponse;
import com.hokhanh.common.util.CloudinarySignatureUtil;
import com.hokhanh.common.util.EnvLoaderUtil;


@Service
public class CloudinaryService {
	
	private static final String API_KEY = EnvLoaderUtil.getCloudinaryApiKey();

	public CloudinaryUploadApiResponse createSignatureInAvatarUpload(String userId){
		String publicId = generatePublicId("artist", null, userId);
		return createCloudinarySignature("avatars", "image", publicId);
	}
	
	public CloudinaryUploadApiResponse createSignatureInProjectMusicUpload(String userId){
		String publicId = generatePublicId("project", "music", userId);
		return createCloudinarySignature("projects/music", "video", publicId);
	}
	
	public CloudinaryUploadApiResponse createSignatureInProjectImageUpload(String userId){
		String publicId = generatePublicId("project", "image", userId);
		return createCloudinarySignature("projects/images", "image", publicId);
	}
	
	private String generatePublicId(String prefix, String suffix, String userId) {
		return prefix + "_" + userId + (suffix != null ? "_" + suffix : "");
	}
	
	private CloudinaryUploadApiResponse createCloudinarySignature( String folder, String resourceType, String publicId) {
		long timestamp = System.currentTimeMillis() / 1000;
		boolean overwrite = true;
		String signature = CloudinarySignatureUtil.createSignature(publicId, folder, overwrite, timestamp);
		
		if(signature == null || signature.isBlank()) {
			return new CloudinaryUploadApiResponse(false, "Signature is error", null);
		}
		return new CloudinaryUploadApiResponse(true, "Create signature successfully", 
				new CloudinaryUploadResponse(folder, overwrite, publicId, resourceType, timestamp, API_KEY, signature));
	}
}