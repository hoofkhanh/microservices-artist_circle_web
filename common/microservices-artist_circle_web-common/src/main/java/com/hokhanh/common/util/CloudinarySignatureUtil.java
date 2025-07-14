package com.hokhanh.common.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class CloudinarySignatureUtil {
	private static final String API_SECRET = EnvLoaderUtil.getCloudinaryApiSecret();
	
	public static String createSignature(
            String publicId,
            String folder,
            boolean overwrite,
            long timestamp
    ) {
        StringBuilder toSign = new StringBuilder();

        if (folder != null && !folder.isBlank()) {
            toSign.append("folder=").append(folder).append("&");
        }
        
        if (Boolean.TRUE.equals(overwrite)) {
            toSign.append("overwrite=").append(overwrite).append("&");
        }

        if (publicId != null && !publicId.isBlank()) {
            toSign.append("public_id=").append(publicId).append("&");
        }

        toSign.append("timestamp=").append(timestamp);
        
        toSign.append(API_SECRET);
        
        // Hash SHA1
        return sha1Hex(toSign.toString());
    }

    private static String sha1Hex(String data) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            byte[] hash = digest.digest(data.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-1 algorithm not supported", e);
        }
    }
}
