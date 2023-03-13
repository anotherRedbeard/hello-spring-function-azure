package com.example;


import java.util.function.Function;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class DemoApplication {

    @Bean
	public Function<String, String> uppercase() {
		return payload -> payload.toUpperCase();
	}
    
    public static void main(String[] args) throws Exception {
        SpringApplication.run(HelloFunction.class, args);
    }
}
