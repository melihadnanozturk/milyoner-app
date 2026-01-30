package org.maoco.milyoner.common.security;

import io.github.bucket4j.redis.lettuce.cas.LettuceBasedProxyManager;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

/**
 * Redis configuration for rate limiting with Bucket4j.
 */
@Configuration
public class RedisConfig {
    
    @Value("${spring.data.redis.host:localhost}")
    private String redisHost;
    
    @Value("${spring.data.redis.port:6379}")
    private int redisPort;
    
    @Value("${spring.data.redis.password:}")
    private String redisPassword;
    
    @Value("${redis.ssl:false}")
    private boolean sslEnabled;
    
    private void logRedisConfig(String message, String data) {
        try {
            java.io.FileWriter fw = new java.io.FileWriter("/home/adnan/Desktop/projects/milyoner-app/.cursor/debug.log", true);
            String logEntry = String.format("{\"sessionId\":\"debug-session\",\"runId\":\"run1\",\"hypothesisId\":\"A\",\"location\":\"RedisConfig.java:35\",\"message\":\"%s\",\"data\":\"%s\",\"timestamp\":%d}\n", 
                message.replace("\"", "\\\""), data.replace("\"", "\\\""), System.currentTimeMillis());
            fw.write(logEntry);
            fw.close();
        } catch (Exception e) {}
    }
    
    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {
        String configData = String.format("host=%s,port=%d,ssl=%s,hasPassword=%s", 
            redisHost, redisPort, sslEnabled, (redisPassword != null && !redisPassword.isEmpty()));
        logRedisConfig("RedisConfig initialization", configData);
        // #endregion
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
        config.setHostName(redisHost);
        config.setPort(redisPort);
        if (redisPassword != null && !redisPassword.isEmpty()) {
            config.setPassword(redisPassword);
        }
        
        LettuceClientConfiguration.LettuceClientConfigurationBuilder builder = 
            LettuceClientConfiguration.builder()
                .commandTimeout(Duration.ofSeconds(2));
        
        if (sslEnabled) {
            builder.useSsl();
        }
        
        LettuceClientConfiguration clientConfig = builder.build();
        return new LettuceConnectionFactory(config, clientConfig);
    }
    
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(new StringRedisSerializer());
        template.afterPropertiesSet();
        return template;
    }
    
    @Bean
    public LettuceBasedProxyManager<byte[]> bucket4jProxyManager(RedisConnectionFactory connectionFactory) {
        RedisURI.Builder uriBuilder = RedisURI.builder()
                .withHost(redisHost)
                .withPort(redisPort);
        
        if (sslEnabled) {
            uriBuilder.withSsl(true);
        }
        
        if (redisPassword != null && !redisPassword.isEmpty()) {
            uriBuilder.withPassword(redisPassword.toCharArray());
        }
        
        RedisURI redisURI = uriBuilder.build();
        RedisClient redisClient = RedisClient.create(redisURI);
        
        return LettuceBasedProxyManager.builderFor(redisClient)
                .build();
    }
}
