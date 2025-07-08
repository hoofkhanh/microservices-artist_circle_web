package com.hokhanh.web.user.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.hokhanh.common.config.JwtAutoConfiguration;

@Import(JwtAutoConfiguration.class)
@Configuration
public class JwtConfiguration {

}
