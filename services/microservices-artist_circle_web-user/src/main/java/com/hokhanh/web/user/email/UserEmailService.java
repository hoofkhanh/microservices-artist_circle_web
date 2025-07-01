package com.hokhanh.web.user.email;

import org.springframework.stereotype.Service;

import com.hokhanh.web.user.constant.ExpirationConstants;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserEmailService {
	private final EmailService emailService;

	public void sendOtpToGmail(String email, String otp) {
		emailService.sendGmail(
	            email,
	            String.format("Your OTP is valid for %d minutes", ExpirationConstants.OTP_MINUTES),
	            String.format("""
	                If you fail to enter the OTP within %d minutes,
	                you'll need to re-enter all registration info
	                OTP: %s""", 
	                ExpirationConstants.USER_MINUTES, otp)
	        );
	}
}
