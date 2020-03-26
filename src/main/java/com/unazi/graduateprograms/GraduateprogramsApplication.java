package com.unazi.graduateprograms;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class GraduateprogramsApplication {

    @Value("#{ @environment['allowed.origins'] ?: {} }")
    private String[] allowedOrigins;

    public static void main(String[] args) {
        SpringApplication.run(GraduateprogramsApplication.class, args);
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins(allowedOrigins).allowCredentials(true);
            }
        };
    }



}
