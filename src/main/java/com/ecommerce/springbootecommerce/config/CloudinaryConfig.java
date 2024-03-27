package com.ecommerce.springbootecommerce.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cloudinary.Cloudinary;
import com.ecommerce.springbootecommerce.constant.CloudinaryConstant;

@Configuration
public class CloudinaryConfig {

    @Bean
    public Cloudinary cloudinary() {
        Map<String, String> config = new HashMap<>();
        config.put("cloud_name", CloudinaryConstant.CLOUD_NAME);
        config.put("api_key", CloudinaryConstant.API_KEY);
        config.put("api_secret", CloudinaryConstant.API_SECRET);

        return new Cloudinary(config);
    }
}
