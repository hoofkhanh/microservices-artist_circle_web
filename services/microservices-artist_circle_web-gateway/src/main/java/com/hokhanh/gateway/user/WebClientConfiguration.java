package com.hokhanh.gateway.user;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfiguration {

	@Bean
	@LoadBalanced
	WebClient.Builder webClientBuilder() {
	    return WebClient.builder();
	}

}
