package com.example.BillingSystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.example.BillingSystem"})
public class BillingSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(BillingSystemApplication.class, args);
    }
}
