package com.slalom.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class LuckyNumberApplication {

	public static void main(String[] args) {
		SpringApplication.run(LuckyNumberApplication.class, args);
	}
}
