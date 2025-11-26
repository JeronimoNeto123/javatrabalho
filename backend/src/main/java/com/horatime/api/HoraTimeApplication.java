package com.horatime.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Classe principal da aplicação Spring Boot.
 * Inicia o servidor de aplicação e a API REST do HoraTime.
 */
@SpringBootApplication
public class HoraTimeApplication {

    public static void main(String[] args) {
        SpringApplication.run(HoraTimeApplication.class, args);
    }
}
