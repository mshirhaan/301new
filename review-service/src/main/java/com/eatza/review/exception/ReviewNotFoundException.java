package com.eatza.review.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ReviewNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ReviewNotFoundException() {
		super();
	}

	public ReviewNotFoundException(String msg) {
		super(msg);
	}

}
