package com.example.agrisupportandtorism;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class AgriSupportAndTorismApplication {

    public static void main(String[] args) {
        SpringApplication.run(AgriSupportAndTorismApplication.class, args);
    }

}
