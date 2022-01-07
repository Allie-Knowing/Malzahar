package com.foreveryone.knowing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class KnowingApplication {

	public static void main(String[] args) {
		SpringApplication.run(KnowingApplication.class, args);
	}

}
