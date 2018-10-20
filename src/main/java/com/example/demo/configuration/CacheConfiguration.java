package com.example.demo.configuration;

import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.MemoryUnit;
import org.ehcache.expiry.Duration;
import org.ehcache.expiry.Expirations;
import org.ehcache.jsr107.Eh107Configuration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.jcache.JCacheCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.spi.CachingProvider;
import java.util.stream.Stream;

import static java.util.concurrent.TimeUnit.SECONDS;

@Configuration
public class CacheConfiguration {


    @Bean
    public JCacheCacheManager cacheManager() {
        CachingProvider cachingProvider = Caching.getCachingProvider();
        CacheManager cacheManager = cachingProvider.getCacheManager();

        org.ehcache.config.CacheConfiguration<Object, Object> defaultConfig = CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.newResourcePoolsBuilder().offheap(10, MemoryUnit.MB).build())
                .withExpiry(Expirations.timeToLiveExpiration(Duration.of(100, SECONDS)))
                .build();

        getCaches().filter(c -> cacheManager.getCache(c) == null).forEach(c ->
                cacheManager.createCache(c, Eh107Configuration.fromEhcacheCacheConfiguration(defaultConfig))
        );

        return new JCacheCacheManager(cacheManager);
    }

    private Stream<String> getCaches() {
        return Stream.of("createInvocationScriptFolder");
    }
}
