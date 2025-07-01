package com.hokhanh.web.user.redis;

import java.time.Duration;

import org.springframework.stereotype.Service;

import com.hokhanh.web.user.constant.ExpirationConstants;
import com.hokhanh.web.user.constant.RedisKeyConstants;
import com.hokhanh.web.user.request.UserRequest;
import com.hokhanh.web.user.util.OtpUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserRedisService {
	private final RedisService redisService;
	
	public void cacheUserRequest(UserRequest userRequest) {
		redisService.setValue(RedisKeyConstants.USER_BY_EMAIL + userRequest.email(), userRequest,
				Duration.ofMinutes(ExpirationConstants.USER_MINUTES));
	}
	
	public String generateAndCacheOtp(String email) {
		String otp = OtpUtil.generateSecureOtp();
		redisService.setValue(RedisKeyConstants.OTP_BY_EMAIL + email, otp, Duration.ofMinutes(ExpirationConstants.OTP_MINUTES));
		return otp;
	}
	
	public UserRequest getCachedUserRequest(String email) {
		return redisService.getValue(RedisKeyConstants.USER_BY_EMAIL + email, UserRequest.class);
	}
	
	public String getCachedOtp(String email) {
		return redisService.getValue(RedisKeyConstants.OTP_BY_EMAIL + email, String.class);
	}
	
	public void deleteCachedOtpAndUserRequest(String email) {
		redisService.deleteKey(RedisKeyConstants.USER_BY_EMAIL + email);
		redisService.deleteKey(RedisKeyConstants.OTP_BY_EMAIL + email);
	}
}
