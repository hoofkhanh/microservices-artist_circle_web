package com.hokhanh.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.hokhanh.common.jwt.JwtService;

@Configuration
public class JwtAutoConfiguration {

	@Bean
	JwtService jwtService() {
		return new JwtService();
	}
}
