package org.maoco.milyoner.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Rate limiting annotation for protecting endpoints against brute force attacks.
 * 
 * @param limit Number of requests allowed
 * @param windowMinutes Time window in minutes
 * @param keyType Type of key to use for rate limiting (IP, USERNAME, etc.)
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RateLimit {
    
    /**
     * Maximum number of requests allowed
     */
    int limit() default 5;
    
    /**
     * Time window in minutes
     */
    int windowMinutes() default 1;
    
    /**
     * Type of key to use for rate limiting
     */
    KeyType keyType() default KeyType.IP;
    
    enum KeyType {
        IP,        // Rate limit by IP address
        USERNAME   // Rate limit by username (for authenticated requests)
    }
}
