package com.eatza.order.service.itemservice;

import java.util.List;

import com.eatza.order.model.OrderedItem;

public interface ItemService {
	
	public OrderedItem saveItem(OrderedItem item);
	public List<OrderedItem> findbyOrderId(Long id);
	public void deleteItemsById(Long id);

}
