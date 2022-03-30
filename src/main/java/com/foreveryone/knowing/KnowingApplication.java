package com.foreveryone.knowing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@ConfigurationPropertiesScan
@SpringBootApplication
public class KnowingApplication {

	public static void main(String[] args) {
		SpringApplication.run(KnowingApplication.class, args);
	}

}
