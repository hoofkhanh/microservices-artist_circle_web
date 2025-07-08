package com.hokhanh.artist.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;

import graphql.schema.Coercing;
import graphql.schema.CoercingParseLiteralException;
import graphql.schema.CoercingParseValueException;
import graphql.schema.CoercingSerializeException;
import graphql.schema.GraphQLScalarType;
import org.springframework.web.multipart.MultipartFile;

@Configuration
public class GraphQLScalarTypeConfiguration {
	
	@Bean
	RuntimeWiringConfigurer runtimeWiringConfigurer(GraphQLScalarType uploadScalar) {
	    return wiringBuilder -> wiringBuilder.scalar(uploadScalar);
	}
	
	@Bean
	GraphQLScalarType uploadScalar() {
		return GraphQLScalarType.newScalar().name("Upload").description("Custom Upload scalar")
				.coercing(new Coercing<MultipartFile, Void>() {

					@Override
					public MultipartFile parseValue(Object input) {
						if (input instanceof MultipartFile multipartFile) {
							return multipartFile;
						}
						throw new CoercingParseValueException("Expected MultipartFile");
					}

					@Override
					public MultipartFile parseLiteral(Object input) {
						throw new CoercingParseLiteralException("Literal parsing not supported");
					}

					@Override
					public Void serialize(Object dataFetcherResult) {
						throw new CoercingSerializeException("Serialization not supported");
					}
				}).build();
	}
}
