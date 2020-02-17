package com.eatza.restaurantsearch.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler{
	
	@ExceptionHandler(UnauthorizedException.class)
	public ResponseEntity<Object> exception(UnauthorizedException exception) {
		
		 return new ResponseEntity<>("Invalid Credentials", HttpStatus.UNAUTHORIZED);
	}
	
	
	@ExceptionHandler(RestaurantNotFoundException.class)
	ResponseEntity<Object> exception(RestaurantNotFoundException exception) {
		
		 return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
	}
	@ExceptionHandler(RestaurantBadRequestException.class)
	ResponseEntity<Object> exception(RestaurantBadRequestException exception) {
		
		 return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
	}
	@ExceptionHandler(ItemNotFoundException.class)
	ResponseEntity<Object> exception(ItemNotFoundException exception) {
		
		 return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(InvalidTokenException.class)
	ResponseEntity<Object> exception(InvalidTokenException exception) {
		
		 return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
	}
}
