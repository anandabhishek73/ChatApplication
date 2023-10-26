package com.anand.abhishek.chat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class ChatApplication {

	private static Logger logger = LoggerFactory.getLogger(ChatApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(ChatApplication.class, args);

		System.out.println("Hello world from Chat application");
	}

	@PostConstruct
	void postInit(){
		logger.info("CHAT WEB APPLICATION STARTED on port : {} ", 8080);
	}


}
