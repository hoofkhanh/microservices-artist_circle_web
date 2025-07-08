package com.hokhanh.common.util;

import java.io.IOException;
import java.util.Map;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

public final class CloudinaryUploadUtil {
	
	private static final Cloudinary CLOUDINARY = EnvLoaderUtil.getCloudinary();
	
	@SuppressWarnings("unchecked")
	public static Map<String, Object> upload(byte[] data, String folder, String resourceType, String publicId) throws IOException {
		Map<String, Object> params = ObjectUtils.asMap(
				"folder", folder,
				"resource_type", resourceType,
			    "use_filename", false,
			    "unique_filename", false,
			    "public_id", publicId,
			    "overwrite", true
			);
		
		return CLOUDINARY.uploader().upload(data, params);
	}

	private CloudinaryUploadUtil() {}
}
