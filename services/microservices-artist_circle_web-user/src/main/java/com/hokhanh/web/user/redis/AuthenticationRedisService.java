package com.hokhanh.web.user.redis;

import java.time.Duration;

import org.springframework.stereotype.Service;

import com.hokhanh.web.user.constant.AuthenticationConstants;
import com.hokhanh.web.user.jwt.JwtProperties;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationRedisService {
	private final RedisService redisService;
	private final JwtProperties jwtProperties;

	public void cacheRefreshToken(String refreshToken) {
		redisService.setValue(AuthenticationConstants.REFRESH_TOKEN_REDIS_KEY + refreshToken, refreshToken,
				Duration.ofMillis(jwtProperties.getRefreshExpiration()));
	}

	public void putAccessTokenToBlackList(String accessToken, long ttl) {
		redisService.setValue(AuthenticationConstants.BLACK_ACCESS_TOKEN_REDIS_KEY + accessToken, accessToken, Duration.ofSeconds(ttl));
	}

	public String getCacheRefreshToken(String refreshToken) {
		return redisService.getValue(AuthenticationConstants.REFRESH_TOKEN_REDIS_KEY + refreshToken, String.class);
	}

	public void revokeRefreshToken(String refreshToken) {
		redisService.deleteKey(AuthenticationConstants.REFRESH_TOKEN_REDIS_KEY + refreshToken);
	}

	public boolean isTokenInBlacklist(String accessToken) {
		String token = redisService.getValue(AuthenticationConstants.BLACK_ACCESS_TOKEN_REDIS_KEY + accessToken, String.class);
		return token != null ? true : false;
	}
	
}
