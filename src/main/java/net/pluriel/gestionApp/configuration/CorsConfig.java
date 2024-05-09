package net.pluriel.gestionApp.configuration;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {


    @Override
    public void addCorsMappings(CorsRegistry registry) {
       registry.addMapping("/**")
                .allowedOrigins("*") // Allow requests from the specified URL
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("*")
                .maxAge(3600);
    }
}
