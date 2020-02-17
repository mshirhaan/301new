package com.eatza.customer.exception;

import java.time.LocalDate;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(Exception.class)
	public final ResponseEntity<Object> handleNotFoundExceptions(Exception ex, WebRequest request) throws Exception {
	ExceptionResponse exceptionRepsonse = new ExceptionResponse(LocalDate.now(), ex.getMessage(), request.getDescription(false));
	
	return new ResponseEntity(exceptionRepsonse, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(CustomerNotFoundException.class)
	public final ResponseEntity<Object> handleCustomerNotFoundExceptions(CustomerNotFoundException ex, WebRequest request) throws Exception {
	ExceptionResponse exceptionRepsonse = new ExceptionResponse(LocalDate.now(), ex.getMessage(), request.getDescription(false));
	
	return new ResponseEntity(exceptionRepsonse, HttpStatus.NOT_FOUND);
	}

}
