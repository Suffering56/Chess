package com.example.chess;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Log4j2
@SpringBootApplication
public class App implements CommandLineRunner {

    public static final String APP_VERSION = "v.0.0.3";

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Override
    public void run(String... args) {
        log.info("APP_VERSION = " + APP_VERSION);
    }
}
