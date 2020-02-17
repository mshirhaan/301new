package com.eatza.restaurantsearch.exception;

public class MenuNotSavedException extends RuntimeException {
	
	public MenuNotSavedException() {
		super();
	}
	public MenuNotSavedException(String msg) {
		super(msg);
	}

}
