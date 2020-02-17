package com.eatza.restaurantsearch.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class RestaurantRequestDto {
	
	private String name;
	private String location;
	private String cuisine;
	private int budget;
	private double rating;
	private String activeFrom;
	private String activeTill;


}
