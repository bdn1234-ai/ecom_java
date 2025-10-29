package com.example.ecom.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // ✅ "file:uploads/" nghĩa là thư mục nằm cùng cấp với project root
        registry.addResourceHandler("/img/category_img/**")
                .addResourceLocations("file:uploads/img/category_img/");
    }
}
