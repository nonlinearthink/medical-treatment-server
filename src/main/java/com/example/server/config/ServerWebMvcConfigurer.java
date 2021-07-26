package com.example.server.config;

import com.example.server.interceptor.CorsInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * @author nonlinearthink
 */
@Configuration
public class ServerWebMvcConfigurer implements WebMvcConfigurer {

    @Value("${file.image.store-path}")
    private String imageStorePath;

    @Value("${file.image.url-mapping}")
    private String imageUrlMapping;

    @Resource
    private CorsInterceptor corsInterceptor;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(imageUrlMapping + "**").addResourceLocations("file:" + imageStorePath);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(corsInterceptor).addPathPatterns("/**");
//        WebMvcConfigurer.super.addInterceptors(registry);
    }
}
