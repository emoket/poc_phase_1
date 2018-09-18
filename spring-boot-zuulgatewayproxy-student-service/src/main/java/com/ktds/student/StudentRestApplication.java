package com.ktds.student;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@EnableEurekaClient
@RestController
@SpringBootApplication
@RefreshScope
public class StudentRestApplication {
	@Value("${msg:Config Server is not working}")
	private String msg;
	
	@RequestMapping(value="/echoStudentName/{name}")
	public String echoStudentName(@PathVariable(name="name") String name)
	{
		return "hello <strong style=\"color: red;\">" + name + " </strong> Responsed on : " + new Date() + "<strong style=\"color: red;\">" + msg + " </strong>";
	}
	
	@RequestMapping(value="/getStudentDetails/{name}")
	public Student getStudentDetails(@PathVariable(name="name") String name)
	{
		return new Student(name, "Pune", "MCA");
	}
	
	public static void main(String[] args) {
		SpringApplication.run(StudentRestApplication.class, args);
	}
}
