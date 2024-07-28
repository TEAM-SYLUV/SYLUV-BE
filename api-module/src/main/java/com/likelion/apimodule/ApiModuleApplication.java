package com.likelion.apimodule;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@ComponentScan(basePackages = "com.likelion")
public class ApiModuleApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiModuleApplication.class, args);
    }

}
