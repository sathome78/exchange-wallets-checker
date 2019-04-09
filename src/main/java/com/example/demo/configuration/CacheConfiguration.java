package com.example.demo.configuration;

import com.amazonaws.services.simplesystemsmanagement.AWSSimpleSystemsManagement;
import com.amazonaws.services.simplesystemsmanagement.AWSSimpleSystemsManagementClientBuilder;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.Cache;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class CacheConfiguration {

    public final static String CACHE_ETHEREUM_SEARCH = "cache.ethereum.search";

    @Bean(CACHE_ETHEREUM_SEARCH)
    public Cache cacheEthereumSearch() {
        return new CaffeineCache(CACHE_ETHEREUM_SEARCH, Caffeine.newBuilder()
                .expireAfterWrite(2, TimeUnit.MINUTES)
                .build());
    }

    @Bean
    public AWSSimpleSystemsManagement ssmClient() {
        return AWSSimpleSystemsManagementClientBuilder.defaultClient();
    }

    @Bean
    public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setPoolSize(100);
        return threadPoolTaskScheduler;
    }
}
