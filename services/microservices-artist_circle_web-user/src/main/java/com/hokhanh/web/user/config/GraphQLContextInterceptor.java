package com.hokhanh.web.user.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.server.WebGraphQlInterceptor;
import org.springframework.graphql.server.WebGraphQlRequest;
import org.springframework.graphql.server.WebGraphQlResponse;
import org.springframework.http.HttpCookie;

import com.hokhanh.web.user.constant.AuthenticationConstants;
import com.hokhanh.web.user.jwt.JwtProperties;
import com.hokhanh.web.user.util.CookieUtil;

import graphql.GraphQLContext;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Configuration
@RequiredArgsConstructor
public class GraphQLContextInterceptor implements WebGraphQlInterceptor {
	
	private final JwtProperties jwtProperties;

	@Override
	public Mono<WebGraphQlResponse> intercept(WebGraphQlRequest request, Chain chain) {
		// here run before go into controller
		HttpCookie refreshTokenFromCookie = request.getCookies()
				.getFirst(AuthenticationConstants.REFRESH_TOKEN_COOKIE_NAME);
		String authorization = request.getHeaders().getFirst("Authorization");
		request.configureExecutionInput((executionInput, builder) -> {
			Map<String, Object> contextMap = new HashMap<>();
			if (refreshTokenFromCookie != null) {
				contextMap.put(AuthenticationConstants.REFRESH_TOKEN_COOKIE_NAME, refreshTokenFromCookie.getValue());
			}
			if (authorization != null) {
				contextMap.put(AuthenticationConstants.AUTHORIZATION_CONTEXT_KEY, authorization);
			}
			return builder.graphQLContext(contextMap).build();
		});

		return chain.next(request).doOnNext(response -> {
			// here run after finish request
			GraphQLContext ctx = response.getExecutionInput().getGraphQLContext();
			Boolean setCookie = ctx.getOrDefault(AuthenticationConstants.SET_COOKIE_CONTEXT_KEY, false);

			if (Boolean.TRUE.equals(setCookie)) {
				String refreshTokenFromContext = ctx.get(AuthenticationConstants.REFRESH_TOKEN_COOKIE_NAME);
				CookieUtil.setHttpOnlyCookie(response, AuthenticationConstants.REFRESH_TOKEN_COOKIE_NAME,
						refreshTokenFromContext, jwtProperties.getRefreshExpiration(),
						AuthenticationConstants.REFRESH_TOKEN_COOKIE_PATH);
			}

			Boolean removeCookie = ctx.getOrDefault(AuthenticationConstants.REMOVE_COOKIE_CONTEXT_KEY, false);
			if (Boolean.TRUE.equals(removeCookie)) {
				CookieUtil.setHttpOnlyCookie(response, AuthenticationConstants.REFRESH_TOKEN_COOKIE_NAME, null, 0,
						AuthenticationConstants.REFRESH_TOKEN_COOKIE_PATH);
			}

		});
	}
}
