package com.week2.chatgptapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.week2.chatgptapi.repository")
public class ChatgptapiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChatgptapiApplication.class, args);
    }

}
