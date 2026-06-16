package com.ridenshine.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.net.URI;

@SpringBootApplication
public class RideNShineApplication {
    public static void main(String[] args) {
        configureRenderDatabase();
        SpringApplication.run(RideNShineApplication.class, args);
    }

    private static void configureRenderDatabase() {
        String databaseUrl = System.getenv("DATABASE_URL");
        if (databaseUrl == null || databaseUrl.isBlank()) return;
        try {
            URI uri = URI.create(databaseUrl);
            String[] credentials = uri.getUserInfo().split(":", 2);
            String jdbcUrl = "jdbc:postgresql://" + uri.getHost() + ":" + uri.getPort() + uri.getPath();
            System.setProperty("spring.datasource.url", jdbcUrl);
            System.setProperty("spring.datasource.username", credentials[0]);
            System.setProperty("spring.datasource.password", credentials.length > 1 ? credentials[1] : "");
        } catch (Exception ex) {
            throw new IllegalStateException("DATABASE_URL could not be parsed", ex);
        }
    }
}
