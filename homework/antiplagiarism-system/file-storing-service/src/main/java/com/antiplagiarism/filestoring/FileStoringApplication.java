package com.antiplagiarism.filestoring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class FileStoringApplication {
    public static void main(String[] args) {
        SpringApplication.run(FileStoringApplication.class, args);
    }
}