package com.hokhanh.search.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.hokhanh.common.config.GraphQLHeaderContextInterceptorAutoConfiguration;
import com.hokhanh.common.graphQL.GraphQLValidationExceptionHandler;


@Configuration
@Import({
	GraphQLHeaderContextInterceptorAutoConfiguration.class,
	GraphQLValidationExceptionHandler.class
})
public class GraphQLSupportConfiguration {
	
	

}
