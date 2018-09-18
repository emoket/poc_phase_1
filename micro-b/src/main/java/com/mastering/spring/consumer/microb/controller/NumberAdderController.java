package com.mastering.spring.consumer.microb.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class NumberAdderController {
	private Log log = LogFactory.getLog(NumberAdderController.class);

	@Value("${number.service.url}")
	private String numberServiceUrl;
	
	@Value("${message}")
	private String messageVal;
	
	@RequestMapping("/add")
	public Long add() {
		long sum = 0;
		
		//micro-a 서버를 호출하기 위한 REST Client
		ResponseEntity<Integer[]> responseEntity 
		                               = new RestTemplate().getForEntity(numberServiceUrl, Integer[].class);
		Integer[] numbers = responseEntity.getBody();
		for(int number : numbers) {
			sum += number;
		}
		
		log.warn("Returning "+ sum);
		return sum;
	}
	
	@RequestMapping("/message")
	public Map<String, String> welcome() {
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("message", messageVal);
		
		return map;
	}
	
}
