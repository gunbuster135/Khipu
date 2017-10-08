package io.einharjar.configuration;


import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import io.einharjar.domain.model.dto.TokenShallow;
import io.einharjar.domain.persistence.entity.Account;
import io.einharjar.domain.persistence.entity.Token;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.util.Pair;

import java.util.concurrent.TimeUnit;

@Configuration
public class CacheConfiguration {
    @Value("${authentication.cache.size}")
    private Integer authCacheSize;

    @Value("${authentication.cache.expire_after_write_seconds}")
    private Integer expireAfterWriteSeconds;

    @Value("${authentication.cache.expire_after_access_seconds}")
    private Integer expireAfterAccessSeconds;

    /**
     * Authentication cache for storing tokens.
     * @return
     */
    @Bean
    public Cache<String, Pair<Account, Token>> authCache(){
        return Caffeine.newBuilder()
                       .maximumSize(authCacheSize)
                       .expireAfterWrite(expireAfterWriteSeconds, TimeUnit.SECONDS)
                       .expireAfterAccess(expireAfterAccessSeconds, TimeUnit.SECONDS)
                       .build();
    }
}
