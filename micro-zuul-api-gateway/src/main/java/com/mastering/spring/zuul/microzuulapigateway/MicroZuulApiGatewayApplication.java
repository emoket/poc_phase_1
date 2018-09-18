package com.mastering.spring.zuul.microzuulapigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

import com.mastering.spring.zuul.microzuulapigateway.filter.ErrorFilter;
import com.mastering.spring.zuul.microzuulapigateway.filter.PostFilter;
import com.mastering.spring.zuul.microzuulapigateway.filter.PreFilter;
import com.mastering.spring.zuul.microzuulapigateway.filter.RouteFilter;


@EnableZuulProxy
@EnableDiscoveryClient
@RefreshScope
@SpringBootApplication
public class MicroZuulApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroZuulApiGatewayApplication.class, args);
	}
	
	/**
	 * HJP 코드추가 - 시작 
	 * @return
	 */
	@RefreshScope
	@Bean
	public PreFilter preFilter() {
		return new PreFilter();
	}
	
	@Bean
	public PostFilter postFilter() {
		return new PostFilter();
	}
	
	@Bean
	public ErrorFilter errorFilter() {
		return new ErrorFilter();
	}
	
	@Bean
	public RouteFilter routeFilter() {
		return new RouteFilter();
	}
	//HJP코드추가 - 끝 
	
}
