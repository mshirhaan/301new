package com.eatza.order.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;



@JsonIgnoreProperties(ignoreUnknown = true)
public class RestaurantFetchDto {

	private Long id;
	private String name;
	private String location;
	private String cuisine;
	private int budget;
	private double rating;
	public Long getId() {
		return id;
	}
	
	public RestaurantFetchDto(Long id, String name, String location, String cuisine, int budget, double rating) {
		super();
		this.id = id;
		this.name = name;
		this.location = location;
		this.cuisine = cuisine;
		this.budget = budget;
		this.rating = rating;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getCuisine() {
		return cuisine;
	}

	public void setCuisine(String cuisine) {
		this.cuisine = cuisine;
	}

	public int getBudget() {
		return budget;
	}

	public void setBudget(int budget) {
		this.budget = budget;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public RestaurantFetchDto() {
		super();
	}

}
