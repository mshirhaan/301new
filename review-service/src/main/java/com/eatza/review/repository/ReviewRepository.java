package com.eatza.review.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eatza.review.model.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer>{
	
}
