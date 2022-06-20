package com.fabrizio.rooftopchallenge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class RooftopchallengeApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(RooftopchallengeApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(RooftopchallengeApplication.class, args);
	}

}
