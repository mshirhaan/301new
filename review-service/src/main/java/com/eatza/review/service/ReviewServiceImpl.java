package com.eatza.review.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eatza.review.dto.ReviewDto;
import com.eatza.review.exception.ReviewNotFoundException;
import com.eatza.review.model.Review;
import com.eatza.review.repository.ReviewRepository;

@Service
public class ReviewServiceImpl implements ReviewService {

	@Autowired ReviewRepository reviewRepository;

	@Override
	public Review submitReview(ReviewDto reviewDto) {
		Review review = new Review(reviewDto);
		reviewRepository.save(review);
		review.setPort(reviewDto.getPort());
		return review;
	}

	@Override
	public boolean updateReview(ReviewDto reviewDto, Integer reviewId) {
		Optional<Review> reviewOtp = reviewRepository.findById(reviewId);
		if (reviewOtp.isPresent()) {
			Review review = reviewOtp.get();
			review.setCustomerId(reviewDto.getCustomerId());
			review.setRestaurantId(reviewDto.getRestaurantId());
			review.setRating(reviewDto.getRating());
			review.setReview(reviewDto.getReview());
			review.setUpdateDateTime(LocalDateTime.now());
			reviewRepository.save(review);
			return true;
		}
		return false;
	}

	@Override
	public Review getReview(Integer reviewId) {
		Optional<Review> review = reviewRepository.findById(reviewId);
		if(review.isPresent()) {
			return review.get();
		} else {
				throw new ReviewNotFoundException("No records found for respective id");
			}
	}
}
