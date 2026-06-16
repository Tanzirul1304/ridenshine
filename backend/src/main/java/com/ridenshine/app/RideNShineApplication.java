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

        if (databaseUrl == null || databaseUrl.isBlank()) {
            return;
        }

        try {
            URI uri = URI.create(databaseUrl);

            if (uri.getHost() == null || uri.getUserInfo() == null) {
                throw new IllegalArgumentException("Invalid PostgreSQL connection URL");
            }

            String[] credentials = uri.getUserInfo().split(":", 2);

            // Render can omit the port because PostgreSQL uses 5432 by default.
            int port = uri.getPort() == -1 ? 5432 : uri.getPort();

            String jdbcUrl =
                    "jdbc:postgresql://"
                            + uri.getHost()
                            + ":"
                            + port
                            + uri.getPath();

            if (uri.getQuery() != null && !uri.getQuery().isBlank()) {
                jdbcUrl += "?" + uri.getQuery();
            }

            System.setProperty("spring.datasource.url", jdbcUrl);
            System.setProperty("spring.datasource.username", credentials[0]);
            System.setProperty(
                    "spring.datasource.password",
                    credentials.length > 1 ? credentials[1] : ""
            );

        } catch (Exception exception) {
            throw new IllegalStateException(
                    "DATABASE_URL could not be parsed",
                    exception
            );
        }
    }
}