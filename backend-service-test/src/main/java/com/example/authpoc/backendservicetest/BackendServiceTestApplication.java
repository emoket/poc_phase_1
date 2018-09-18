package com.example.authpoc.backendservicetest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class BackendServiceTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendServiceTestApplication.class, args);
	}
}
