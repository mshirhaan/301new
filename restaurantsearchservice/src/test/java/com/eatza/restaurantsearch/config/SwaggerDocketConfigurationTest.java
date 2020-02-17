package com.eatza.restaurantsearch.config;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.UiConfiguration;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes=SwaggerConfiguration.class)
public class SwaggerDocketConfigurationTest {
	
	@Autowired
	Docket docket;
	
	
	@Autowired
	UiConfiguration uiconfiguration;
	
	@Test
	public void docketTest() {
		assertNotNull(docket);
	}
	
	@Test
	public void uiconfigurationTest() {
		assertNotNull(uiconfiguration);
	}

}
