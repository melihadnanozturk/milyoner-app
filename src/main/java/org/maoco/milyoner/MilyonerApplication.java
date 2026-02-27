package org.maoco.milyoner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class MilyonerApplication {

    public static void main(String[] args) {
        // #region agent log
        System.out.println("[DEBUG-A] Application starting... checking .env dependency");
        // #endregion
        try {
            SpringApplication.run(MilyonerApplication.class, args);
            // #region agent log
            System.out.println("[DEBUG-A] Spring context loaded successfully");
            // #endregion
        } catch (Exception e) {
            // #region agent log
            System.out.println("[DEBUG-A] Spring context FAILED to load: " + e.getMessage());
            if (e.getCause() != null) {
                System.out.println("[DEBUG-A] Root cause: " + e.getCause().getMessage());
            }
            e.printStackTrace();
            // #endregion
            throw e;
        }
    }

    // #region agent log
    @Bean
    CommandLineRunner debugStartupRunner(Environment env) {
        return args -> {
            System.out.println("[DEBUG-B] DB_URL present: " + (env.getProperty("DB_URL") != null && !env.getProperty("DB_URL", "").isEmpty()));
            System.out.println("[DEBUG-B] DB_URL starts with: " + safePrefix(env.getProperty("DB_URL", "")));
            System.out.println("[DEBUG-C] REDIS_HOST present: " + (env.getProperty("REDIS_HOST") != null && !env.getProperty("REDIS_HOST", "").isEmpty()));
            System.out.println("[DEBUG-C] REDIS_HOST value: " + env.getProperty("REDIS_HOST", "NOT_SET"));
            System.out.println("[DEBUG-C] REDIS_PORT value: " + env.getProperty("REDIS_PORT", "NOT_SET"));
            System.out.println("[DEBUG-D] Max memory: " + (Runtime.getRuntime().maxMemory() / 1024 / 1024) + "MB");
            System.out.println("[DEBUG-D] Total memory: " + (Runtime.getRuntime().totalMemory() / 1024 / 1024) + "MB");
            System.out.println("[DEBUG-E] SPRING_PROFILES_ACTIVE: " + env.getProperty("SPRING_PROFILES_ACTIVE", "NOT_SET"));
            System.out.println("[DEBUG-E] CORS_ALLOWED_ORIGINS present: " + (env.getProperty("CORS_ALLOWED_ORIGINS") != null));
            System.out.println("[DEBUG-E] JWT_SECRET present: " + (env.getProperty("JWT_SECRET") != null && !env.getProperty("JWT_SECRET", "").isEmpty()));
            System.out.println("[DEBUG-OK] Application started successfully on port " + env.getProperty("server.port", "8080"));
        };
    }

    private static String safePrefix(String val) {
        if (val == null || val.isEmpty()) return "EMPTY";
        return val.substring(0, Math.min(val.length(), 20)) + "...";
    }
    // #endregion

}
