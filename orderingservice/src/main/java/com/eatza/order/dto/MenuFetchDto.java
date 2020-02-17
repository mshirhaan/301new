package com.eatza.order.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class MenuFetchDto {

	
	private Long id;
	private String activeFrom;
	private String activeTill;
	@JsonProperty("restaurant")
	private RestaurantFetchDto restaurant;

	public MenuFetchDto(Long id, String activeFrom, String activeTill, RestaurantFetchDto restaurant) {
		super();
		this.id = id;
		this.activeFrom = activeFrom;
		this.activeTill = activeTill;
		this.restaurant = restaurant;
	}
	
	
}
