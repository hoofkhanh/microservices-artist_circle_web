package com.hokhanh.web.user.jwt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class JwtProperties {
	@Value("${jwt.secret-key}")
	private String secretKey;

	@Value("${jwt.expiration}")
	private long jwtExpiration;

	@Value("${jwt.refresh-token.expiration}")
	private long refreshExpiration;
}
