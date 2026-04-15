package com.example.hex;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.example.hex")
public class HexagonalServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(HexagonalServiceApplication.class, args);
    }
}
