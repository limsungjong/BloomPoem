package com.example.bloompoem.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@PropertySource("classpath:app.properties")
public class WebConfig implements WebMvcConfigurer {

    @Value("#{environment['upload.path']}")
    private String uploadPath;
    @Value("#{environment['custom.file']}")
    private String floristPath;
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/image/upload/**")
                .addResourceLocations(uploadPath);
        registry.addResourceHandler("/image/florist_product/**")
                .addResourceLocations(floristPath);
    }
}
