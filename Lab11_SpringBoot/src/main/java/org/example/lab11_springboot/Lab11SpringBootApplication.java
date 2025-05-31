package org.example.lab11_springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Lab11SpringBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(Lab11SpringBootApplication.class, args);
        // Configuram totul automat, pornind serverul Tomcat pe portul 8081 (aveam 8080 ocupat)
    }

}

// SWAGGER: http://localhost:8081/swagger-ui/#/