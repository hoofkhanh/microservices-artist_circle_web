package com.hokhanh.common.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class JwtService {

	public String generateToken(ClaimsData claimRequest) {
		Map<String, Object> claims = claimRequest.toMap();
		claims.put("type", "access");
		return buildToken(claims, claimRequest.userId(), JwtPropertyConstants.JWT_EXPIRATION);
	}

	public String generateRefreshToken(Long userId) {
		Map<String, Object> claims = new HashMap<>();
	    claims.put("type", "refresh");
	    return buildToken(claims, userId, JwtPropertyConstants.REFRESH_EXPIRATION);
	}

	private String buildToken(Map<String, Object> extraClaims, Long userId, long expiration) {
		return Jwts.builder()
				.setClaims(extraClaims)
				.setSubject(userId.toString())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + expiration))
				.signWith(getSignInKey(), SignatureAlgorithm.HS256)
				.compact();
	}
	
	public boolean isTokenValid(String token) {
		return !isTokenExpired(token);
	}
	
	private boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}
	
	public Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}
	
	public String extractUserId(String token) {
		return extractClaim(token, Claims::getSubject);
	}
	
	private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	public Claims extractAllClaims(String token) {
		return Jwts.parserBuilder().setSigningKey(getSignInKey()).build().parseClaimsJws(token).getBody();
	}

	private Key getSignInKey() {
		byte[] keyBytes = Decoders.BASE64.decode(JwtPropertyConstants.SECRET_KEY);
		return Keys.hmacShaKeyFor(keyBytes);
	}
}
