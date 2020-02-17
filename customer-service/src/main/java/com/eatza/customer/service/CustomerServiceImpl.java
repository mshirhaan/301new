package com.eatza.customer.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eatza.customer.dto.CustomerDto;
import com.eatza.customer.exception.CustomerNotFoundException;
import com.eatza.customer.model.Customer;
import com.eatza.customer.repository.CustomerRepository;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	CustomerRepository customerRepository;

	@Override
	public Customer registerCustomer(CustomerDto customerDto) {
		customerDto.setActive(true);
		Customer customer = new Customer(customerDto);
		customerRepository.save(customer);
		return customer;
	}

	@Override
	public boolean deactivateCustomer(Integer customerId) {
		Customer customer = customerRepository.findByIdAndIsActiveIsTrue(customerId);
		if (customer != null) {
			customer.setActive(false);
			customerRepository.save(customer);
			return true;
		}
		return false;
	}

	@Override
	public boolean updateCustomer(CustomerDto customerDto, Integer customerId) {
		Customer customer = customerRepository.findByIdAndIsActiveIsTrue(customerId);
		if (customer != null) {
			customer.setFirstName(customerDto.getFirstName());
			customer.setLastName(customerDto.getLastName());
			customer.setMiddleName(customerDto.getMiddleName());
			customer.setUpdateDateTime(LocalDateTime.now());
			customerRepository.save(customer);
			return true;
		}
		return false;
	}

	@Override
	public Customer getCustomer(Integer customerId) {
		Optional<Customer> customerOpt = customerRepository.findById(customerId);
		if(customerOpt.isPresent()) {
			return customerOpt.get();
		} else {
			throw new CustomerNotFoundException("No record found for respective id");
		}
	} 
}
