package com.eatza.order.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonIgnoreProperties(ignoreUnknown = true)
public class ItemFetchDto {

	private Long id;
	private String name;
	private String description;
	private double price;
	@JsonProperty("menu")
	private MenuFetchDto menu;


	public Long getId() {
		return id;
	}




	public void setId(Long id) {
		this.id = id;
	}




	public ItemFetchDto() {
		super();
	}




	public String getName() {
		return name;
	}




	public void setName(String name) {
		this.name = name;
	}




	public String getDescription() {
		return description;
	}




	public void setDescription(String description) {
		this.description = description;
	}




	public double getPrice() {
		return price;
	}




	public void setPrice(double price) {
		this.price = price;
	}




	public MenuFetchDto getMenu() {
		return menu;
	}




	public void setMenu(MenuFetchDto menu) {
		this.menu = menu;
	}




	public ItemFetchDto(Long id, String namee, String description, double price, MenuFetchDto menu) {
		super();
		this.id = id;
		this.name = namee;
		this.description = description;
		this.price = price;
		this.menu = menu;
	}




}
