package com.hokhanh.search.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.hokhanh.common.config.RestTemplateAutoConfiguration;

@Configuration
@Import({
	RestTemplateAutoConfiguration.class
})
public class ArtistRestTemplateConfiguration {

}
