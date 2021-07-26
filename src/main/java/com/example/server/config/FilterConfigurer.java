package com.example.server.config;

import com.example.server.filter.AuthFilter;
import com.example.server.filter.CorsFilter;
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
    public FilterRegistrationBean<AuthFilter> authFilterRegistrationBean() {
        FilterRegistrationBean<AuthFilter> bean = new FilterRegistrationBean<>();
        bean.setFilter(getAuthFilter());
        bean.addUrlPatterns("/api/admin/*", "/api/org/*", "/api/dept/*", "/api/doctor/*", "/api/patient/*",
                "/api/diagnosis/*", "/api/drug/*", "/api/consult-ask/*", "/api/prescription-drug/*",
                "/api/prescription/*");
        bean.setName("AuthFilter");
        bean.setOrder(2);
        return bean;
    }

    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilterRegistrationBean() {
        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>();
        bean.setFilter(getCorFilter());
        bean.setOrder(1);
        return bean;
    }

    @Bean
    public AuthFilter getAuthFilter(){
        return new AuthFilter();
    }

    @Bean
    public CorsFilter getCorFilter(){
        return new CorsFilter();
    }

}
