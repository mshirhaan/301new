package com.eatza.restaurantsearch.exception;

public class RestaurantBadRequestException extends RuntimeException {

	public  RestaurantBadRequestException() {
		super();
	}
	public RestaurantBadRequestException(String msg) {
		super(msg);
	}
}
