package com.KamilIsmail.MovieApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableJpaAuditing
@EnableScheduling
@EnableJpaRepositories("com.KamilIsmail.MovieApp.repository")
public class MovieAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(MovieAppApplication.class, args);
    }
}
