package com.hokhanh.web.user.util;

import java.security.SecureRandom;

public class OtpUtil {
	private static SecureRandom random = new SecureRandom();
	
	public static String generateSecureOtp() {
	    int otp = 100000 + random.nextInt(900000);
	    return String.valueOf(otp);
	}
}
