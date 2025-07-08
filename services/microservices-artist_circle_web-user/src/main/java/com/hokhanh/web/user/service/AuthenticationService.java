package com.hokhanh.web.user.service;

import java.util.Date;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hokhanh.common.jwt.ClaimsData;
import com.hokhanh.common.jwt.JwtService;
import com.hokhanh.web.user.constant.AuthenticationConstants;
import com.hokhanh.web.user.model.User;
import com.hokhanh.web.user.redis.AuthenticationRedisService;
import com.hokhanh.web.user.repository.UserRepository;
import com.hokhanh.web.user.request.AuthenticationRequest;
import com.hokhanh.web.user.response.AuthenticationApiResponse;
import com.hokhanh.web.user.response.AuthenticationStatusType;

import graphql.GraphQLContext;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
	private final JwtService jwtService;
	private final AuthenticationRedisService authRedisService;
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	public AuthenticationApiResponse login(AuthenticationRequest request, GraphQLContext context) {
		User user = userRepository.findByEmail(request.email());

		AuthenticationApiResponse error = validateBeforeLogin(user, request.password());
		if (error != null) {
			return error;
		}

		String accessToken = generateTokenCacheAndSetCookie(
				new ClaimsData(user.getId(), user.getEmail(), user.getRole().getName()), context);

		return new AuthenticationApiResponse(true, "Login successfully", null, accessToken);
	}

	public AuthenticationApiResponse logout(String token, String refreshToken, GraphQLContext context) {
		context.put(AuthenticationConstants.REMOVE_COOKIE_CONTEXT_KEY, true);
		authRedisService.revokeRefreshToken(refreshToken);
		putAccessTokenToBlackList(token);
		return new AuthenticationApiResponse(true, "Logout successfully", null, null);
	}

	public boolean isTokenBlocked(String accessToken) {
		return authRedisService.isTokenInBlacklist(accessToken);
	}

	public AuthenticationApiResponse refreshToken(String refreshToken) {
		try {
			AuthenticationApiResponse error = validateBeforeRefreshToken(refreshToken);
			if(error != null) {
				return error;
			}
			
			Long userId = Long.parseLong(jwtService.extractUserId(refreshToken));
			User user = userRepository.findById(userId).orElse(null);
	        if (user == null) {
	            return new AuthenticationApiResponse(false, "User not found", 
	    				AuthenticationStatusType.USER_NOT_FOUND, null);
	        }
			
			var accessToken = jwtService.generateToken(
						new ClaimsData(user.getId(), user.getEmail(), user.getRole().getName())
					);
			
			return new AuthenticationApiResponse(true, "Refresh token is successfully", 
					null, accessToken);
			
		} catch(JwtException e){
			return buildRefreshTokenResponse("Invalid Jwt token");
		}
		catch (Exception e) {
			return buildRefreshTokenResponse("Unexpected server error");
		}
	}

	private AuthenticationApiResponse validateBeforeLogin(User user, String rawPassword) {
		if (user == null) {
			return new AuthenticationApiResponse(false, "Email is invalid", AuthenticationStatusType.EMAIL_INVALID,
					null);
		}

		String password = user.getPassword();
		if (password == null || password.isBlank() || !passwordEncoder.matches(rawPassword, password)) {
			return new AuthenticationApiResponse(false, "Password is invalid",
					AuthenticationStatusType.PASSWORD_INVALID, null);
		}

		return null;
	}

	private String generateTokenCacheAndSetCookie(ClaimsData claimsData, GraphQLContext context) {
		var accessToken = jwtService.generateToken(claimsData);
		var refreshToken = jwtService.generateRefreshToken(claimsData.userId());
		authRedisService.cacheRefreshToken(refreshToken);
		context.put(AuthenticationConstants.REFRESH_TOKEN_COOKIE_NAME, refreshToken); // set cookie for refreshToken in
																						// GraphQLContextInterceptor
																						// class
		context.put(AuthenticationConstants.SET_COOKIE_CONTEXT_KEY, true); // flag in GraphQLContextInterceptor class
		return accessToken;
	}

	private void putAccessTokenToBlackList(String accessToken) {
		Date expiration = jwtService.extractExpiration(accessToken);
		long seconds = calculateSeconds(expiration);
		if (seconds <= 0)
			return;
		authRedisService.putAccessTokenToBlackList(accessToken, seconds);
	}

	private long calculateSeconds(Date expiration) {
		long currentMillis = System.currentTimeMillis();
		long expirationMillis = expiration.getTime();
		long seconds = (expirationMillis - currentMillis) / 1000;
		return seconds;
	}
	
	private AuthenticationApiResponse validateBeforeRefreshToken(String refreshToken) {
		if(refreshToken == null || refreshToken.isBlank() ||
				authRedisService.getCacheRefreshToken(refreshToken) == null) {
			return buildRefreshTokenResponse("Cookie or Redis doesn't contain refresh token");
		}
		
		if (!jwtService.isTokenValid(refreshToken)) {
			return buildRefreshTokenResponse("Refresh token invalid");
		}
		
		return null;
	}
	
	private AuthenticationApiResponse buildRefreshTokenResponse(String message) {
		return new AuthenticationApiResponse(false, message, 
				AuthenticationStatusType.REFRESH_TOKEN_ERROR, null);
	}

}
