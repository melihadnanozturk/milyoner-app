package org.maoco.milyoner.common.aspect;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.maoco.milyoner.common.annotation.RateLimit;
import org.maoco.milyoner.common.security.RateLimitService;
import org.maoco.milyoner.common.util.IpAddressUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * AOP Aspect for intercepting methods annotated with @RateLimit.
 * Applies rate limiting based on IP address or username.
 */
@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class RateLimitAspect {
    
    private final RateLimitService rateLimitService;
    private final IpAddressUtil ipAddressUtil;
    
    @Around("@annotation(rateLimit)")
    public Object rateLimit(ProceedingJoinPoint joinPoint, RateLimit rateLimit) throws Throwable {
        String key = resolveKey(rateLimit);
        
        try {
            rateLimitService.checkRateLimit(
                    key,
                    rateLimit.limit(),
                    rateLimit.windowMinutes()
            );
            
            return joinPoint.proceed();
        } catch (org.maoco.milyoner.common.exception.RateLimitException e) {
            throw e;
        }
    }
    
    /**
     * Resolves the rate limit key based on the annotation configuration.
     * 
     * @param rateLimit RateLimit annotation
     * @return Key for rate limiting (IP address or username)
     */
    private String resolveKey(RateLimit rateLimit) {
        if (rateLimit.keyType() == RateLimit.KeyType.USERNAME) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && authentication.isAuthenticated()) {
                return authentication.getName();
            }
        }
        
        // Default to IP address
        HttpServletRequest request = getCurrentRequest();
        if (request != null) {
            return "ip:" + ipAddressUtil.getClientIpAddress(request);
        }
        
        return "unknown";
    }
    
    /**
     * Gets the current HTTP request from the request context.
     * 
     * @return HttpServletRequest or null if not available
     */
    private HttpServletRequest getCurrentRequest() {
        ServletRequestAttributes attributes = 
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return attributes != null ? attributes.getRequest() : null;
    }
}
