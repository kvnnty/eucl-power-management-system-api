package com.kvn.eucl.powermetersystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableAsync
@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class PowermetersystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(PowermetersystemApplication.class, args);
	}

}
