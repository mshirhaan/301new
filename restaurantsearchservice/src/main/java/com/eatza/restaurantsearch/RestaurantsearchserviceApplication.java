package com.eatza.restaurantsearch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

import com.eatza.restaurantsearch.config.JwtFilter;

@SpringBootApplication
@EnableCaching
@EnableEurekaClient
public class RestaurantsearchserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestaurantsearchserviceApplication.class, args);
	}

	
	@Bean
	public FilterRegistrationBean jwtFilter() {
		final FilterRegistrationBean registrationBean = new FilterRegistrationBean();
		registrationBean.setFilter(new JwtFilter());
		registrationBean.addUrlPatterns("/restaurant/*", "/restaurants/*","/item/");

		return registrationBean;
	}
}
