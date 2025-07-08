package com.hokhanh.artist.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.hokhanh.common.config.GraphQLHeaderContextInterceptorAutoConfiguration;
import com.hokhanh.common.config.GraphQLValidationExceptionHandlerAutoConfiguration;

@Configuration
@Import({
	GraphQLHeaderContextInterceptorAutoConfiguration.class,
	GraphQLValidationExceptionHandlerAutoConfiguration.class
})
public class GraphQLSupportConfiguration {

}
