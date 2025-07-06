package com.hokhanh.artist.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.hokhanh.common.config.RestTemplateConfiguration;

@Configuration
@Import({
	RestTemplateConfiguration.class
})
public class UserRestTemplateConfiguration {

}
