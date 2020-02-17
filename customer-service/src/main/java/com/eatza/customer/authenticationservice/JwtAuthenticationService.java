package com.eatza.customer.authenticationservice;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eatza.customer.dto.UserDto;
import com.eatza.customer.exception.UnauthorizedException;
import com.eatza.customer.model.Customer;
import com.eatza.customer.repository.CustomerRepository;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtAuthenticationService {
	
	private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationService.class);

	
	private static final long EXPIRATION_TIME = 900000;
	
	@Autowired CustomerRepository customerRepository;
	
	public String authenticateUser(UserDto user) throws UnauthorizedException {
		Customer customer = customerRepository.findByUsernameAndIsActiveIsTrue(user.getUsername());
		if(customer == null) {
			logger.debug("Username is invalid");
			throw new UnauthorizedException("Invalid Credentials");
		}
		if(!customer.getPassword().equals(user.getPassword())){
			logger.debug("Password is invalid");
			throw new UnauthorizedException("Invalid Credentials");
			
		}
		return Jwts.builder().setSubject(customer.getUsername()).claim("roles", "user").setIssuedAt(new Date())
				.signWith(SignatureAlgorithm.HS256, "secretkey").setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)).compact();
		
		
	}

}
