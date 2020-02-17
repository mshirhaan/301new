package com.eatza.customer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.eatza.customer.dto.CustomerDto;
import com.eatza.customer.exception.CustomerNotFoundException;
import com.eatza.customer.model.Customer;
import com.eatza.customer.service.CustomerService;

@RestController
public class CustomerController {
	
	@Autowired CustomerService customerService;
	@Autowired Environment environment;
	@Autowired KafkaTemplate<String, String> kafkaTemplate;
	
	private static final String TOPIC = "testtopic";
	
	@GetMapping("/customer/{customerId}")
	public Customer getCustomer( @PathVariable Integer customerId) {
		Customer customer = customerService.getCustomer(customerId);
		customer.setPort(Integer.parseInt(environment.getProperty("local.server.port")));
		return customer;
	}
	
	@PostMapping("/customer/registerCustomer")
	public ResponseEntity<Customer> registerCustomer(@RequestHeader String authorization, @RequestBody CustomerDto customerDto) {
		Customer customer = customerService.registerCustomer(customerDto);
		kafkaTemplate.send(TOPIC, customer.getUsername()+ " customer is created");
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(customer);
	}
	
	@PutMapping("/customer/deactivateCustomer/{customerId}")
	public ResponseEntity<String> deactivateCustomer(@PathVariable Integer customerId) {
		boolean result = customerService.deactivateCustomer(customerId);
		if(result) {
			return ResponseEntity
					.status(HttpStatus.OK)
					.body("Customer deactivated Successfully");
		} else {
			throw new CustomerNotFoundException("No records found for respective id");
		}	
	}
	
	@PutMapping("/customer/updateCustomer/{customerId}")
	public ResponseEntity<String> updateCustomer(@RequestBody CustomerDto customerDto, @PathVariable Integer customerId) {
		boolean result = customerService.updateCustomer(customerDto, customerId);
		if(result) {
			return ResponseEntity
					.status(HttpStatus.OK)
					.body("Customer updated Successfully");
		} else {
			throw new CustomerNotFoundException("No records found for respective id");
		}	
	}
}
