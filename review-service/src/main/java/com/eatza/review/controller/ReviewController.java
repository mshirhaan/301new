package com.eatza.review.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.eatza.review.Customer;
import com.eatza.review.CustomerServiceProxy;
import com.eatza.review.dto.ReviewDto;
import com.eatza.review.exception.ReviewNotFoundException;
import com.eatza.review.model.Review;
import com.eatza.review.service.ReviewService;

@RestController
public class ReviewController {
	
	@Autowired ReviewService reviewService;
	@Autowired CustomerServiceProxy customerServiceProxy;
	
	@GetMapping("/review/getReview/{reviewId}")
	public ResponseEntity<Review> getReview(@PathVariable Integer reviewId) {
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(reviewService.getReview(reviewId));
	}
	
	@PostMapping("/review/submitReview")
	public ResponseEntity<Review> submitReview(@RequestBody ReviewDto reviewDto) {
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(reviewService.submitReview(reviewDto));
	}
	
	@PostMapping("/review/submitReview-feign")
	public ResponseEntity<Review> submitReviewFeign(@RequestBody ReviewDto reviewDto) {
		Customer customer = customerServiceProxy.getCustomer(reviewDto.getCustomerId());
		reviewDto.setCustomerFirstName(customer.getFirstName());
		reviewDto.setCustomerLastName(customer.getLastName());
		reviewDto.setPort(customer.getPort());
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(reviewService.submitReview(reviewDto));
	}
	
	
	@PutMapping("/customer/updateCustomer/{customerId}")
	public ResponseEntity<String> updateReview(@RequestBody ReviewDto reviewDto, @PathVariable Integer reviewId) {
		boolean result = reviewService.updateReview(reviewDto, reviewId);
		if(result) {
			return ResponseEntity
					.status(HttpStatus.OK)
					.body("Review updated Successfully");
		} else {
			throw new ReviewNotFoundException("No records found for respective id");
		}	
	}
}
