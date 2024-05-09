package ru.veselov.restaurantvoting.configuration;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.RemovalCause;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
@Slf4j
public class CaffeineCacheConfiguration {

    @Bean
    public CacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager("restaurants");
        cacheManager.setCaffeine(caffeineCacheBuilder());
        return cacheManager;
    }

    @Bean
    public Caffeine<Object, Object> caffeineCacheBuilder() {
        return Caffeine.newBuilder()
                .initialCapacity(20)
                .maximumSize(100)
                .expireAfterAccess(1, TimeUnit.HOURS)
                .expireAfterWrite(1, TimeUnit.HOURS)
                .evictionListener((Object key, Object value,
                                   RemovalCause cause) ->
                        log.info("Key %s was evicted (%s)%n".formatted(key, cause)))
                .removalListener((Object key, Object value,
                                  RemovalCause cause) ->
                        log.info("Key %s was removed (%s)%n".formatted(key, cause)))
                .recordStats();
    }
}
