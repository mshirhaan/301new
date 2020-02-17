package com.eatza.order.dto;

import java.util.List;

import com.eatza.order.model.OrderedItem;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class OrderUpdateResponseDto  {

	
	private Long orderId;
	private Long customerId;
	private String status;
	private Long restaurantId;
	private List<OrderedItem> orderedItems;

	public OrderUpdateResponseDto(Long orderId, Long customerId, String status, Long restaurantId,
			List<OrderedItem> orderedItems) {
		super();
		this.orderId = orderId;
		this.customerId = customerId;
		this.status = status;
		this.restaurantId = restaurantId;
		this.orderedItems = orderedItems;
	}

	
}
