package com.eatza.restaurantsearch.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name= "restaurants")
@Getter @Setter @NoArgsConstructor
public class Restaurant {
	
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String location;
	private String cuisine;
	private int budget;
	private double rating;

	public Restaurant(String name, String location, String cuisine, int budget, double rating) {
		super();
		this.name = name;
		this.location = location;
		this.cuisine = cuisine;
		this.budget = budget;
		this.rating = rating;
	}
	
}
