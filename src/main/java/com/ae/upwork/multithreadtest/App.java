package com.ae.upwork.multithreadtest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.ExecutionException;

@SpringBootApplication
@EnableAutoConfiguration
public class App {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        SpringApplication.run(App.class, args);
    }
}
