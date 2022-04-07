package com.ps.cff;

import javax.persistence.Cacheable;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
public class ForecastEstimaterApplication {

	public static void main(String[] args) {
		SpringApplication.run(ForecastEstimaterApplication.class, args);
	}

}
