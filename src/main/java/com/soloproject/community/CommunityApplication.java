package com.soloproject.community;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@EnableBatchProcessing
@SpringBootApplication
public class CommunityApplication {

	public static void main(String[] args) {

		SpringApplication.run(CommunityApplication.class, args);
	}

}
