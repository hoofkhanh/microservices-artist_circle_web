package com.hokhanh.common.graphQL;

import java.util.HashMap;
import java.util.Map;

import org.springframework.graphql.server.WebGraphQlInterceptor;
import org.springframework.graphql.server.WebGraphQlRequest;
import org.springframework.graphql.server.WebGraphQlResponse;

import reactor.core.publisher.Mono;

public class GraphQLHeaderContextInterceptor implements WebGraphQlInterceptor {
	
	@Override
	public Mono<WebGraphQlResponse> intercept(WebGraphQlRequest request, Chain chain) {
		String userId = request.getHeaders().getFirst(HttpHeadersConstants.HEADER_USER_ID);
	    String userRole = request.getHeaders().getFirst(HttpHeadersConstants.HEADER_USER_ROLE);
	    String userEmail = request.getHeaders().getFirst(HttpHeadersConstants.HEADER_USER_EMAIL);
	    
		request.configureExecutionInput((executionInput, builder) -> {
			Map<String, Object> contextMap = new HashMap<>();
			if (userId != null && userRole != null && userEmail != null) {
				contextMap.put(HttpHeadersConstants.HEADER_USER_ID, userId);
				contextMap.put(HttpHeadersConstants.HEADER_USER_ROLE, userRole);
				contextMap.put(HttpHeadersConstants.HEADER_USER_EMAIL, userEmail);
			}
			return builder.graphQLContext(contextMap).build();
		});
		
		return chain.next(request);
	}
}
