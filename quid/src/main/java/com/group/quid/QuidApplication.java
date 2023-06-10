package com.group.quid;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(exclude = {
		org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class})
public class QuidApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuidApplication.class, args);
	}

}
