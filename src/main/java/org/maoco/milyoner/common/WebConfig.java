package org.maoco.milyoner.common;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Tüm endpoint'leri kapsar
                .allowedOrigins("http://localhost:5173") // Sadece bu adrese izin ver
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // İzin verilen metodlar
                .allowedHeaders("*") // Tüm headerlara izin ver
                .allowCredentials(true); // Cookie/Auth bilgilerine izin ver
    }
}
