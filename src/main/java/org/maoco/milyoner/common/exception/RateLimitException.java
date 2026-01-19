package org.maoco.milyoner.common.exception;

import lombok.Getter;

/**
 * Exception thrown when rate limit is exceeded.
 * Returns HTTP 429 (Too Many Requests) status code.
 */

//todo: burada exceotionu milyoner exception olacak şekilde değiştir
@Getter
public class RateLimitException extends RuntimeException {
    
    private final long retryAfterSeconds;
    
    public RateLimitException(String message, long retryAfterSeconds) {
        super(message);
        this.retryAfterSeconds = retryAfterSeconds;
    }
    
    public RateLimitException(String message, long retryAfterSeconds, Throwable cause) {
        super(message, cause);
        this.retryAfterSeconds = retryAfterSeconds;
    }
}
