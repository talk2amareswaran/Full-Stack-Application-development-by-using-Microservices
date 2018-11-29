package com.talk2amar.projects.userservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UserserviceApplication {

	private static final Logger logger = LoggerFactory.getLogger(UserserviceApplication.class);
	
	public static void main(String[] args) {
		logger.info("User Service is starting...");
		SpringApplication.run(UserserviceApplication.class, args);
	}
}
