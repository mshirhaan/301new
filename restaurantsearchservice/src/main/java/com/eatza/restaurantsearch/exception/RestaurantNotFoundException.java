package com.eatza.restaurantsearch.exception;

public class RestaurantNotFoundException extends RuntimeException {
	
	
	public RestaurantNotFoundException() {
		super();
	}
	public RestaurantNotFoundException(String msg) {
		super(msg);
	}

}
