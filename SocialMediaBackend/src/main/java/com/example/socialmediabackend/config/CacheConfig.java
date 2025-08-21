package com.example.socialmediabackend.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class CacheConfig {
    // Spring Boot autoconfigures RedisCacheManager when Redis is present.
}
