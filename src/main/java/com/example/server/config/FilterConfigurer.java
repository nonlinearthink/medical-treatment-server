package com.example.server.config;

import com.example.server.filter.AuthFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;

/**
 * @author nonlinearthink
 */
@Configuration
public class FilterConfigurer {

    @Bean
    public FilterRegistrationBean<Filter> filterRegistrationBean() {
        FilterRegistrationBean<Filter> bean = new FilterRegistrationBean<Filter>();
        bean.setFilter(getAuthFilter());
        bean.addUrlPatterns("/api/admin/*", "/api/org/*", "/api/dept/*");
        bean.setName("AuthFilter");
        return bean;
    }

    @Bean
    public AuthFilter getAuthFilter() {
        return new AuthFilter();
    }

}
