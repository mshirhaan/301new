package com.eatza.restaurantsearch.config;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
	public class TestPropertyConfigs {

	    @Bean
	    public static PropertySourcesPlaceholderConfigurer properties() throws Exception {
	        final PropertySourcesPlaceholderConfigurer pspc = new PropertySourcesPlaceholderConfigurer();
	        Properties properties = new Properties();

	        properties.setProperty("user", "username");
	        properties.setProperty("password", "password");

	        pspc.setProperties(properties);
	        return pspc;
	    }

	}