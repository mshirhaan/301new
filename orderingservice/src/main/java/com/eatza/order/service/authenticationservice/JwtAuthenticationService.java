package com.eatza.order.service.authenticationservice;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.eatza.order.dto.UserDto;
import com.eatza.order.exception.UnauthorizedException;
import com.eatza.order.service.itemservice.ItemServiceImpl;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtAuthenticationService {


	private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationService.class);

	@Value("${user}")
	String username;

	@Value("${password}")
	String password;

	private static final long EXPIRATIONTIME = 900000;


	public String authenticateUser(UserDto user) throws UnauthorizedException {


		if(!(user.getUsername().equals(username))) {
			logger.debug("Username is invalid");

			throw new UnauthorizedException("Invalid Credentials");
		}
		if(!(user.getPassword().equals(password))){
			
			logger.debug("Password is invalid");
			throw new UnauthorizedException("Invalid Credentials");

		}
		return Jwts.builder().setSubject(username).claim("roles", "user").setIssuedAt(new Date())
				.signWith(SignatureAlgorithm.HS256, "secretkey").setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME)).compact();



	}

}
