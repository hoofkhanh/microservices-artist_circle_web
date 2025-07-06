package com.hokhanh.artist.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.hokhanh.common.config.GraphQLHeaderContextInterceptorConfig;
import com.hokhanh.common.config.GraphQLValidationExceptionHandlerConfig;

@Configuration
@Import({
	GraphQLHeaderContextInterceptorConfig.class,
	GraphQLValidationExceptionHandlerConfig.class
})
public class GraphQLSupportConfiguration {

}
