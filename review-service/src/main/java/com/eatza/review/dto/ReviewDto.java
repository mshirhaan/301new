package com.eatza.review.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ReviewDto {

	private Integer customerId;
	private Integer restaurantId;
	private int rating;
	private String review;
	private String customerFirstName;
    private String customerLastName;
    private int port;
}
