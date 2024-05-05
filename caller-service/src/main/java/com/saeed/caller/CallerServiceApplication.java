package com.saeed.caller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class CallerServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CallerServiceApplication.class, args);
	}

}
