package com.khyuna0.khyuna0board;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class Khyuna0ProjectApplication extends SpringBootServletInitializer{

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		// TODO Auto-generated method stub
		return builder.sources(Khyuna0ProjectApplication.class);
	}
	
	public static void main(String[] args) {
		SpringApplication.run(Khyuna0ProjectApplication.class, args);
	}

}
