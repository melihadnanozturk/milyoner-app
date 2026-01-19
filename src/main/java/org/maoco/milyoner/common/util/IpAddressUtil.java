package org.maoco.milyoner.common.util;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

/**
 * Utility class for extracting IP addresses from HTTP requests.
 * Handles proxy headers like X-Forwarded-For and X-Real-IP.
 */
@Component
public class IpAddressUtil {

    //todo: burada değişkenleri value içerisine eklemek gerekir mi ? 
    private static final String X_FORWARDED_FOR_HEADER = "X-Forwarded-For";
    private static final String X_REAL_IP_HEADER = "X-Real-IP";
    private static final String UNKNOWN_IP = "unknown";
    
    /**
     * Extracts the client IP address from the HTTP request.
     * Checks X-Forwarded-For and X-Real-IP headers for proxy/load balancer scenarios.
     * 
     * @param request HTTP servlet request
     * @return Client IP address or "unknown" if cannot be determined
     */
    public String getClientIpAddress(HttpServletRequest request) {
        String ipAddress = request.getHeader(X_FORWARDED_FOR_HEADER);
        
        if (ipAddress == null || ipAddress.isEmpty() || UNKNOWN_IP.equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader(X_REAL_IP_HEADER);
        }
        
        if (ipAddress == null || ipAddress.isEmpty() || UNKNOWN_IP.equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
        }
        
        // X-Forwarded-For can contain multiple IPs, take the first one
        if (ipAddress != null && ipAddress.contains(",")) {
            ipAddress = ipAddress.split(",")[0].trim();
        }
        
        return ipAddress != null ? ipAddress : "unknown";
    }
}
