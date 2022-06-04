package com.michel.plannings;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class PlanningsUiApplication {

	public static void main(String[] args) {
		SpringApplication.run(PlanningsUiApplication.class, args);
	}

}
