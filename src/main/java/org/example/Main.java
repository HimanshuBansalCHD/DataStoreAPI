package org.example;

import org.example.module.ServiceModule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {

        new AnnotationConfigApplicationContext(ServiceModule.class);
        SpringApplication.run(Main.class, args);

    }
}