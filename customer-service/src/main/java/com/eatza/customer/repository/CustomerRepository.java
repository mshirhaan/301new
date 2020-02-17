package com.eatza.customer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eatza.customer.model.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer>{

	Customer findByIdAndIsActiveIsTrue(Integer id);
	
	Customer findByUsernameAndIsActiveIsTrue(String username);
}
