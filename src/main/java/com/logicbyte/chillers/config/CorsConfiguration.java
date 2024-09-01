package com.logicbyte.chillers.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import static com.logicbyte.chillers.util.Constants.*;

/**
 * @author Alessandro Formica
 * @version 1.0
 * @since 21.08.2024
 */

@Configuration
//@EnableWebMvc
public class CorsConfiguration {
//
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
//                .allowCredentials(true).maxAge(3600)
//                .allowedHeaders(ALLOWED_HEADERS.toArray(new String[0]))
//                .allowedOrigins(ALLOWED_ORIGINS.toArray(new String[0]))
//                .allowedMethods(ALLOWED_METHODS.toArray(new String[0]))
//                .exposedHeaders(EXPOSED_HEADERS.toArray(new String[0]));
//        WebMvcConfigurer.super.addCorsMappings(registry);
//    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource corsConfigurationSource = new UrlBasedCorsConfigurationSource();
        org.springframework.web.cors.CorsConfiguration corsConfiguration = new org.springframework.web.cors.CorsConfiguration();
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setAllowedOrigins(ALLOWED_ORIGINS);
        corsConfiguration.setAllowedHeaders(ALLOWED_HEADERS);
        corsConfiguration.setExposedHeaders(EXPOSED_HEADERS);
        corsConfiguration.setAllowedMethods(ALLOWED_METHODS);
        corsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsFilter(corsConfigurationSource);
    }

}
