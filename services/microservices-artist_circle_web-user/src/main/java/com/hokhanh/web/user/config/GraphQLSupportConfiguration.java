package com.hokhanh.web.user.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.hokhanh.common.graphQL.GraphQLValidationExceptionHandler;

@Configuration
@Import(GraphQLValidationExceptionHandler.class)
public class GraphQLSupportConfiguration {

}
