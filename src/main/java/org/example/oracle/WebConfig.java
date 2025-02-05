package org.example.oracle;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // Autoriser CORS pour toutes les routes (/**) venant de http://localhost:4200
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:4200") // URL de votre frontend Angular
                .allowedMethods("GET", "POST", "PUT", "DELETE") // Les méthodes HTTP autorisées
                .allowedHeaders("*") // Autoriser tous les headers
                .allowCredentials(true); // Si vous avez besoin de gérer des cookies ou l'authentification
    }
}

