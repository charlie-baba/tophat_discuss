package com.tophat.discuss;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.tophat.discuss"})
public class DiscussApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(DiscussApiApplication.class, args);
    }

}
