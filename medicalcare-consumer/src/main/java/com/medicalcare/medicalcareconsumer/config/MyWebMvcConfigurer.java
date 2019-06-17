package com.medicalcare.medicalcareconsumer.config;

import com.medicalcare.medicalcareconsumer.filter.AuthoriztionFiilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class MyWebMvcConfigurer extends WebMvcConfigurerAdapter {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthoriztionFiilter()).addPathPatterns("/**");
        super.addInterceptors(registry);
    }
}
