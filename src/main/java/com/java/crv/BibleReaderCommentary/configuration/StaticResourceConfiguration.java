package com.java.crv.BibleReaderCommentary.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/*Resolves the issue of .png files failing to load. /images folder is not detected by default */

@Configuration
public class StaticResourceConfiguration implements WebMvcConfigurer {
	
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/images/**")
		.addResourceLocations("classpath:/static/images");
	}
}
