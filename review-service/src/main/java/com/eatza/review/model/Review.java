package com.eatza.review.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.eatza.review.dto.ReviewDto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="reviews")
@NoArgsConstructor
@Data
public class Review {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    
    private Integer customerId;
    private Integer restaurantId;
    private int rating;
    private String review;
    private LocalDateTime createDateTime;
    private LocalDateTime updateDateTime;
    private String customerFirstName;
    private String customerLastName;
    @Transient private int port;
    
	public Review(ReviewDto reviewDto) {

		this.customerId = reviewDto.getCustomerId();
		this.restaurantId = reviewDto.getRestaurantId();
		this.rating = reviewDto.getRating();
		this.review = reviewDto.getReview();
		this.createDateTime = LocalDateTime.now();
		this.updateDateTime = LocalDateTime.now();
		this.customerFirstName = reviewDto.getCustomerFirstName();
		this.customerLastName = reviewDto.getCustomerLastName();
	}
    
    
}