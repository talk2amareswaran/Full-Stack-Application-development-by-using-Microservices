package com.talk2amar.projects.productservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProductserviceApplication {

	private static final Logger logger = LoggerFactory.getLogger(ProductserviceApplication.class);
	
	public static void main(String[] args) {
		logger.info("Product Service application is starting");
		SpringApplication.run(ProductserviceApplication.class, args);
	}
}
