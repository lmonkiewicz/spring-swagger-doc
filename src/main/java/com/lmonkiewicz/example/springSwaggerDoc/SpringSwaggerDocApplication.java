package com.lmonkiewicz.example.springSwaggerDoc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@SpringBootApplication
@ComponentScan(basePackageClasses = SpringSwaggerDocApplication.class)
public class SpringSwaggerDocApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringSwaggerDocApplication.class, args);
	}
}
