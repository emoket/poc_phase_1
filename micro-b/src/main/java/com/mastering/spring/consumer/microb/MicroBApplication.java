package com.mastering.spring.consumer.microb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class MicroBApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroBApplication.class, args);
	}
}
