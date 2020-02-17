package com.eatza.customer.service;

import com.eatza.customer.dto.CustomerDto;
import com.eatza.customer.model.Customer;

public interface CustomerService {

	Customer registerCustomer(CustomerDto customerDto);
	boolean deactivateCustomer(Integer customerId);
	boolean updateCustomer(CustomerDto customerDto, Integer customerId);
	Customer getCustomer(Integer customerId);
}
