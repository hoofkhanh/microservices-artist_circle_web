package com.hokhanh.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.hokhanh.common.graphQL.GraphQLValidationExceptionHandler;

@Configuration
public class GraphQLValidationExceptionHandlerAutoConfiguration {
	
	@Bean
    GraphQLValidationExceptionHandler validationExceptionHandler() {
        return new GraphQLValidationExceptionHandler();
    }
}
