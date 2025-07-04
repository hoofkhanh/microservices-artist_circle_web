package com.hokhanh.gateway.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.micrometer.common.util.StringUtils;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import com.hokhanh.gateway.user.UserWebClient;

import reactor.core.publisher.Mono;

@Component
public class JwtAuthorizationFilter implements WebFilter {
	
	private static final List<String> ADMIN_REQUIRED_OPERATIONS = List.of();
	private static final List<String> ARTIST_REQUIRED_OPERATIONS = List.of();
	
	private static final String ADMIN_ROLE = "ADMIN";
	private static final String ARTIST_ROLE = "ARTIST";
	
	private static final String HEADER_USER_ID  = "X-User-Id";
	private static final String HEADER_USER_ROLE  = "X-User-Role";
	
	@Autowired
	private UserWebClient userWebClient;

	@Autowired
	private JwtService jwtService;

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
		ServerHttpRequest request = exchange.getRequest();

		String requireJwt = request.getHeaders().getFirst(JwtAuthUtils.HEADER_REQUIRE_JWT);
		if ("false".equalsIgnoreCase(requireJwt)) {
			return chain.filter(exchange);
		}

		// check
		String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			return JwtAuthUtils.onError(exchange, "Do not empty bearer", HttpStatus.UNAUTHORIZED);
		}

		String token = authHeader.substring(7).trim();
		if (StringUtils.isEmpty(token)) {
			return JwtAuthUtils.onError(exchange, "Token is empty", HttpStatus.UNAUTHORIZED);
		}
		
		try {
			if (!jwtService.isTokenValid(token) ) {
				return JwtAuthUtils.onError(exchange, "Token is invalid", HttpStatus.UNAUTHORIZED);
			}
			
			return userWebClient.isTokenBlocked(token)
				.flatMap(isBlocked -> {
					if(isBlocked) {
						return JwtAuthUtils.onError(exchange, "Token is blocked", HttpStatus.UNAUTHORIZED);
					}
					
					return processValidateToken(exchange, chain, token);
				});
		} catch (JwtException e) {
			return JwtAuthUtils.onError(exchange, e.getMessage(), HttpStatus.UNAUTHORIZED);
		} catch (Exception e) {
			return JwtAuthUtils.onError(exchange, e.getMessage(), HttpStatus.UNAUTHORIZED);
		}
	}
	
	private Mono<Void> processValidateToken(ServerWebExchange exchange, WebFilterChain chain, String token){
		Claims claims = jwtService.extractAllClaims(token);

		String tokenType = claims.get("type", String.class);
		if (!"access".equals(tokenType)) {
			return JwtAuthUtils.onError(exchange, "Token type must be access", HttpStatus.UNAUTHORIZED);
		}

		String userId = claims.getSubject();
		String roleName = claims.get("roleName", String.class);
		String email = claims.get("email", String.class);
		
		if (StringUtils.isEmpty(userId) || StringUtils.isEmpty(email) || StringUtils.isEmpty(roleName)) {
			return JwtAuthUtils.onError(exchange, "The claims data is empty", HttpStatus.UNAUTHORIZED);
		}
		
		return checkRole(exchange, exchange.getRequest(), roleName)
				.switchIfEmpty(Mono.defer(() -> {
					ServerHttpRequest mutatedRequest = exchange.getRequest().mutate()
							.header(HEADER_USER_ID, userId)
							.header(HEADER_USER_ROLE, roleName).build();

					ServerWebExchange mutatedExchange = exchange.mutate().request(mutatedRequest).build();

					return chain.filter(mutatedExchange);
				}));
	}
	
	private Mono<Void> checkRole(ServerWebExchange exchange, ServerHttpRequest request, String roleName){
		String operationName = request.getHeaders().getFirst(JwtAuthUtils.HEADER_OPERATION_NAME);
		if(ADMIN_REQUIRED_OPERATIONS
				.stream().anyMatch(x -> x.equalsIgnoreCase(operationName))
				&& !ADMIN_ROLE.equalsIgnoreCase(roleName)) {
			return JwtAuthUtils.onError(exchange, "THIS REQUEST IS ADMIN ROLE", HttpStatus.FORBIDDEN);
		}
		
		if(ARTIST_REQUIRED_OPERATIONS
				.stream().anyMatch(x -> x.equalsIgnoreCase(operationName))
				&& !ARTIST_ROLE.equalsIgnoreCase(roleName)) {
			return JwtAuthUtils.onError(exchange, "THIS REQUEST IS ARTIST ROLE", HttpStatus.FORBIDDEN);
		}
		
		return Mono.empty();
	}
}
