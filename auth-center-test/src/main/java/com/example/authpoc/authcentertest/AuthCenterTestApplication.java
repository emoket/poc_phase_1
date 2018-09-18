package com.example.authpoc.authcentertest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class AuthCenterTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthCenterTestApplication.class, args);
	}
}
