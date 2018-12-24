package com.example.demo.configuration;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

import static com.google.common.cache.CacheBuilder.*;

@Configuration
@EnableCaching
public class CacheConfiguration {


    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager("getEthTokens") {
            protected Cache createConcurrentMapCache(final String name) {
                return new ConcurrentMapCache(name, newBuilder().expireAfterWrite(20, TimeUnit.MINUTES)
                                                                .build()
                                                                .asMap(), false);
            }
        };
    }

}
