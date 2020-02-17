package com.eatza.orderingservice.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import com.eatza.order.model.Order;
import com.eatza.order.model.OrderedItem;
import com.eatza.order.repository.OrderedItemRepository;
import com.eatza.order.service.itemservice.ItemServiceImpl;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class ItemServiceTest {
	
	@Mock
	OrderedItemRepository itemRepository;
	
	@InjectMocks
	ItemServiceImpl itemServiceImpl;
	
	
	@Test
	public void saveItem_basic() {
		Order order  = new Order(1L, "UPDATED", 1L);
		OrderedItem orderedItem = new OrderedItem();
		orderedItem.setName("Dosa");
		orderedItem.setId(1L);
		orderedItem.setOrder(order);
		orderedItem.setPrice(100);
		orderedItem.setQuantity(2);
		when(itemRepository.save(any(OrderedItem.class))).thenReturn(orderedItem);
		OrderedItem returnedItem = itemServiceImpl.saveItem(orderedItem);
		assertEquals("Dosa", returnedItem.getName());
		assertEquals(order, returnedItem.getOrder());
		assertEquals(2, returnedItem.getQuantity());
	}
	
	
	@Test
	public void findByOrderId_basic() {
		Order order  = new Order(1L, "UPDATED", 1L);
		OrderedItem orderedItem = new OrderedItem("Dosa", 2, 100, order, 1L);
		List<OrderedItem> orderedItems = new ArrayList<>();
		orderedItems.add(orderedItem);
		when(itemRepository.findbyOrderId(anyLong())).thenReturn(orderedItems);
		List<OrderedItem> items =  itemServiceImpl.findbyOrderId(1L);
		assertEquals("Dosa",items.get(0).getName());
		
	}
	
	@Test
	public void deleteItemsById() {
		
		Mockito.doNothing().when(itemRepository).deleteById(anyLong());
		itemServiceImpl.deleteItemsById(1L);
	}
	
	

}
