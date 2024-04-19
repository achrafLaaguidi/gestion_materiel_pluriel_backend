package net.pluriel.gestionApp.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:3000")// permettre l'accès à partir de ce domaine
                        .allowedMethods("GET", "POST", "PUT", "DELETE") // méthodes HTTP autorisées
                        .allowedHeaders("*") // autoriser tous les en-têtes
                        .allowCredentials(true) // autoriser l'envoi de cookies
                        .maxAge(3600); // durée en secondes pour laquelle la réponse peut être mise en cache
            }
        };
    }
}
