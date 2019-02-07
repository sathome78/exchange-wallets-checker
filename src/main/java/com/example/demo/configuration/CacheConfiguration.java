package com.example.demo.configuration;

import com.amazonaws.services.simplesystemsmanagement.AWSSimpleSystemsManagement;
import com.amazonaws.services.simplesystemsmanagement.AWSSimpleSystemsManagementClientBuilder;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.util.concurrent.TimeUnit;

import static com.google.common.cache.CacheBuilder.newBuilder;

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

    @Bean
    public AWSSimpleSystemsManagement ssmClient(){
        return AWSSimpleSystemsManagementClientBuilder.defaultClient();
    }

    @Bean
    public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setPoolSize(100);
        return threadPoolTaskScheduler;
    }

}
