package com.hokhanh.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.server.WebGraphQlInterceptor;

import com.hokhanh.common.graphQL.GraphQLHeaderContextInterceptor;

@Configuration
public class GraphQLHeaderContextInterceptorAutoConfiguration {

	@Bean
	WebGraphQlInterceptor interceptor() {
		return new GraphQLHeaderContextInterceptor();
	}
}
