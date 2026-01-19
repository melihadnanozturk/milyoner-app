package org.maoco.milyoner.common.security;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.BucketConfiguration;
import io.github.bucket4j.redis.lettuce.cas.LettuceBasedProxyManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.maoco.milyoner.common.exception.RateLimitException;
import org.springframework.stereotype.Service;

import java.time.Duration;

/**
 * Service for managing rate limiting using Bucket4j with Redis backend.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RateLimitService {
    
    private final LettuceBasedProxyManager<byte[]> proxyManager;
    
    /**
     * Checks if the request is allowed based on rate limit configuration.
     * 
     * @param key Unique key for rate limiting (e.g., IP address or username)
     * @param limit Maximum number of requests allowed
     * @param windowMinutes Time window in minutes
     * @throws RateLimitException if rate limit is exceeded
     */
    public void checkRateLimit(String key, int limit, int windowMinutes) {
        Bucket bucket = resolveBucket(key, limit, windowMinutes);
        
        if (!bucket.tryConsume(1)) {
            long retryAfterSeconds = calculateRetryAfter(windowMinutes);
            
            log.warn("Rate limit exceeded for key: {}. Limit: {}/{} minutes. Retry after {} seconds", 
                    key, limit, windowMinutes, retryAfterSeconds);
            
            throw new RateLimitException(
                    String.format("Rate limit exceeded. Maximum %d requests per %d minutes allowed. Please try again later.", 
                            limit, windowMinutes),
                    retryAfterSeconds
            );
        }
    }
    
    /**
     * Resolves or creates a bucket for the given key.
     * 
     * @param key Unique key for rate limiting
     * @param limit Maximum number of requests
     * @param windowMinutes Time window in minutes
     * @return Bucket instance
     */
    private Bucket resolveBucket(String key, int limit, int windowMinutes) {
        Bandwidth bandwidth = Bandwidth.builder()
                .capacity(limit)
                .refillIntervally(limit, Duration.ofMinutes(windowMinutes))
                .build();
        
        BucketConfiguration configuration = BucketConfiguration.builder()
                .addLimit(bandwidth)
                .build();
        
        return proxyManager.builder()
                .build(key.getBytes(), configuration);
    }
    
    /**
     * Calculates retry after seconds based on the time window.
     * 
     * @param windowMinutes Time window in minutes
     * @return Retry after seconds
     */
    private long calculateRetryAfter(int windowMinutes) {
        // Return the full window time in seconds as retry after
        return windowMinutes * 60L;
    }
}
