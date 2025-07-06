package com.hokhanh.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.hokhanh.common.handler.GraphQLValidationExceptionHandler;

@Configuration
public class GraphQLValidationExceptionHandlerConfig {
	
	@Bean
    GraphQLValidationExceptionHandler validationExceptionHandler() {
        return new GraphQLValidationExceptionHandler();
    }
}
