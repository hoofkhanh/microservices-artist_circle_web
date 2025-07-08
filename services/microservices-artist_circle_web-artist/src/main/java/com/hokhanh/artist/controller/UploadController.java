package com.hokhanh.artist.controller;

import java.io.IOException;

import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;

import com.hokhanh.artist.service.CloudinaryService;
import com.hokhanh.common.cloudinary.dto.CloudinaryApiResponse;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class UploadController {
	private final CloudinaryService cloudinaryService;
	
	@MutationMapping
	public CloudinaryApiResponse uploadAvatar(MultipartFile file) throws IOException {
		return cloudinaryService.uploadAvatar(file);
	}
	
	@MutationMapping
	public CloudinaryApiResponse uploadProjectMusic(MultipartFile file) throws IOException {
		return cloudinaryService.uploadProjectMusic(file);
	}
	
	@MutationMapping
	public CloudinaryApiResponse uploadProjectImage(MultipartFile file) throws IOException {
		return cloudinaryService.uploadProjectImage(file);
	}
}
