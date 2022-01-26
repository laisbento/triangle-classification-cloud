package com.cocus.triangleclassification.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.handler.MappedInterceptor;

@Configuration
public class SpringConfig {

    final AppInterceptor appInterceptor;

    public SpringConfig(AppInterceptor appInterceptor) {
        this.appInterceptor = appInterceptor;
    }

    @Bean
    public MappedInterceptor addInterceptors() {
        return new MappedInterceptor(null, appInterceptor);
    }

}