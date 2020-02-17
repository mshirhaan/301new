package com.eatza.restaurantsearch.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class ItemRequestDto {
	
	private Long menuId;
	private String name;
	private String description;
	private int price;
	


}
