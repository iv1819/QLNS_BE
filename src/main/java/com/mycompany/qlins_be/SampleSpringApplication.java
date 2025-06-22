package com.mycompany.qlins_be;

import java.nio.file.Files;
import java.nio.file.Paths;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * Main class to start the Spring Boot application.
 */
@SpringBootApplication
public class SampleSpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(SampleSpringApplication.class, args);
    }
    @Bean
CommandLineRunner ensureUploadDir(@Value("${upload.dir}") String dir) {
    return args -> Files.createDirectories(Paths.get(dir));
}

}
