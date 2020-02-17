package com.eatza.restaurantsearch.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter @NoArgsConstructor
public class Menu {

	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Long id;
	private String activeFrom;
	private String activeTill;

	@OneToOne
	@JoinColumn(name = "restaurant_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Restaurant restaurant;


	public Menu(String activeFrom, String activeTill, Restaurant restaurant ) {
		this.restaurant = restaurant;
		this.activeFrom = activeFrom;
		this.activeTill = activeTill;
	}






}
