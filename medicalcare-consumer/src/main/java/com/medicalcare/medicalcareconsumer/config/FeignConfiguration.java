package com.medicalcare.medicalcareconsumer.config;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Component
public class FeignConfiguration {

    @Bean
    public RequestInterceptor headerInterceptor() {
        return requestTemplate -> {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
                    .getRequestAttributes();
            if (attributes == null) {
                return;
            }
            HttpServletRequest request = attributes.getRequest();
            String token= request.getHeader("Authoriztion");
            if (!StringUtils.isEmpty(token)) {
                requestTemplate.header("Authoriztion", token);
            }
        };
    }
}
