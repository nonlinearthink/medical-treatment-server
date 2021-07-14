package com.example.server.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author nonlinearthink
 */
@Configuration
public class ServerWebMvcConfigurer implements WebMvcConfigurer {

    @Value("${file.image.store-path}")
    private String imageStorePath;

    @Value("${file.image.url-mapping}")
    private String imageUrlMapping;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(imageUrlMapping + "**").addResourceLocations("file:" + imageStorePath);
    }

}
