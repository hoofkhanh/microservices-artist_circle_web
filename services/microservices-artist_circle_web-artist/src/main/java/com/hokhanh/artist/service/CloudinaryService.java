package com.hokhanh.artist.service;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hokhanh.common.cloudinary.dto.CloudinaryApiResponse;
import com.hokhanh.common.cloudinary.dto.CloudinaryResponse;
import com.hokhanh.common.util.CloudinaryUploadUtil;

@Service
public class CloudinaryService {

	public CloudinaryApiResponse uploadAvatar(MultipartFile file) throws IOException{
		String publicId = generatePublicId("artist", null);
		return uploadToCloudinary(file, "avatars", "image", publicId);
	}
	
	public CloudinaryApiResponse uploadProjectMusic(MultipartFile file) throws IOException{
		String publicId = generatePublicId("project", "music");
		return uploadToCloudinary(file, "projects/music", "video", publicId);
	}
	
	public CloudinaryApiResponse uploadProjectImage(MultipartFile file) throws IOException{
		String publicId = generatePublicId("project", "image");
		return uploadToCloudinary(file, "projects/images", "image", publicId);
	}
	
	private String generatePublicId(String prefix, String suffix) {
		return prefix + "_" + UUID.randomUUID() + (suffix != null ? "_" + suffix : "");
	}
	
	private CloudinaryApiResponse uploadToCloudinary(MultipartFile file, String folder, String resourceType, String publicId) throws IOException {
		if(file == null || file.isEmpty()) {
			return new CloudinaryApiResponse(false, "File is mandatory", null);
		}
		
		Map<String, Object> result = CloudinaryUploadUtil.upload(file.getBytes(), folder, resourceType, publicId);
		
		Object publicIdObj = result.get("public_id");
		Object secureUrlObj = result.get("secure_url");
		
		if (publicIdObj == null || secureUrlObj == null) {
			return new CloudinaryApiResponse(false, "Cloudinary did not return expected fields", null);
		}
		
		return new CloudinaryApiResponse(true, "Upload successfully", 
				new CloudinaryResponse(publicIdObj.toString(), secureUrlObj.toString())
		);
	}
}
