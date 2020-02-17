package com.eatza.order.dto;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class OrderUpdateDto {
	
	private Long customerId;
	private Long restaurantId;
	private List<OrderedItemsDto> items;
	private Long orderId;
	
	
	public OrderUpdateDto(Long customerId, Long restaurantId, List<OrderedItemsDto> items, Long orderId) {
		super();
		this.customerId = customerId;
		this.restaurantId = restaurantId;
		this.items = items;
		this.orderId = orderId;
	}

	
	

	
	
	
	

}
