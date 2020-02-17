package com.eatza.orderingservice.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import com.eatza.order.dto.ItemFetchDto;
import com.eatza.order.dto.MenuFetchDto;
import com.eatza.order.dto.OrderRequestDto;
import com.eatza.order.dto.OrderUpdateDto;
import com.eatza.order.dto.OrderUpdateResponseDto;
import com.eatza.order.dto.OrderedItemsDto;
import com.eatza.order.dto.RestaurantFetchDto;
import com.eatza.order.exception.OrderException;
import com.eatza.order.model.Order;
import com.eatza.order.model.OrderedItem;
import com.eatza.order.repository.OrderRepository;
import com.eatza.order.service.itemservice.ItemServiceImpl;
import com.eatza.order.service.orderservice.OrderServiceImpl;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class OrderServiceTest {

	@InjectMocks
	private OrderServiceImpl orderService;

	@Mock
	private OrderRepository orderRepository;

	@Mock
	private ItemServiceImpl itemService;

	@Mock
	private RestTemplate restTemplate;



	@Test
	public void placeOrder_basic() throws OrderException {
		OrderedItemsDto orderedItemsDto = new OrderedItemsDto(1L, 0);
		orderedItemsDto.setItemId(1L);
		orderedItemsDto.setQuantity(1);


		OrderRequestDto orderRequest = new OrderRequestDto();
		orderRequest.setCustomerId(1L);
		orderRequest.setRestaurantId(1L);
		orderRequest.setItems(Arrays.asList(orderedItemsDto));
		RestaurantFetchDto restaurant = new RestaurantFetchDto();
		restaurant.setBudget(400);
		restaurant.setRating(4.2);
		restaurant.setCuisine("South Indian");
		restaurant.setId(1L);
		restaurant.setName("Vasudev");
		restaurant.setLocation("RR Nagar");
		
		MenuFetchDto menu = new MenuFetchDto();
		menu.setId(1L);
		menu.setActiveFrom("10");
		menu.setActiveTill("22");
		menu.setRestaurant(restaurant);
		ItemFetchDto item = new ItemFetchDto();
		item.setDescription("Dosa");
		item.setId(1L);
		item.setMenu(menu);
		item.setName("Onion Dosa");
		item.setPrice(110);


		when(orderRepository.save(any(Order.class)))
		.thenReturn(new Order(orderRequest.getCustomerId(), "CREATED", orderRequest.getRestaurantId()));

		when(restTemplate.getForObject(any(String.class),any(Class.class))).thenReturn(item);
		when(itemService.saveItem(any(OrderedItem.class))).thenReturn(new OrderedItem());

		Order order = orderService.placeOrder(orderRequest);
		assertNotNull(order);

	}

	@Test(expected=OrderException.class)
	public void placeOrder_different_restaurant() throws OrderException {
		OrderedItemsDto orderedItemsDto = new OrderedItemsDto(1L, 0);
		orderedItemsDto.setItemId(1L);
		orderedItemsDto.setQuantity(1);


		OrderRequestDto orderRequest = new OrderRequestDto();
		orderRequest.setCustomerId(1L);
		orderRequest.setRestaurantId(1L);
		orderRequest.setItems(Arrays.asList(orderedItemsDto));
		RestaurantFetchDto restaurant = new RestaurantFetchDto(2L, "Vasudev", "RR Nagar", "South Indian", 400, 4.2);
		MenuFetchDto menu = new MenuFetchDto(1L, "10", "22", restaurant);
		ItemFetchDto item = new ItemFetchDto(1L, "Onion Dosa", "Dosa", 110, menu);


		when(orderRepository.save(any(Order.class)))
		.thenReturn(new Order(orderRequest.getCustomerId(), "CREATED", orderRequest.getRestaurantId()));

		when(restTemplate.getForObject(any(String.class),any(Class.class))).thenReturn(item);
		//		when(itemService.saveItem(any(OrderedItem.class))).thenReturn(new OrderedItem());

		Order order = orderService.placeOrder(orderRequest);
		assertNotNull(order);

	}
	@Test(expected=OrderException.class)
	public void placeOrder_no_item() throws OrderException {
		OrderedItemsDto orderedItemsDto = new OrderedItemsDto(1L, 0);
		orderedItemsDto.setItemId(1L);
		orderedItemsDto.setQuantity(1);


		OrderRequestDto orderRequest = new OrderRequestDto();
		orderRequest.setCustomerId(1L);
		orderRequest.setRestaurantId(1L);
		orderRequest.setItems(Arrays.asList(orderedItemsDto));
		RestaurantFetchDto restaurant = new RestaurantFetchDto(1L, "Vasudev", "RR Nagar", "South Indian", 400, 4.2);
		MenuFetchDto menu = new MenuFetchDto(1L, "10", "22", restaurant);
		ItemFetchDto item = new ItemFetchDto(1L, "Onion Dosa", "Dosa", 110, menu);


		when(orderRepository.save(any(Order.class)))
		.thenReturn(new Order(orderRequest.getCustomerId(), "CREATED", orderRequest.getRestaurantId()));

		when(restTemplate.getForObject(any(String.class),any(Class.class))).thenReturn(null);
		//		when(itemService.saveItem(any(OrderedItem.class))).thenReturn(new OrderedItem());

		Order order = orderService.placeOrder(orderRequest);
		assertNotNull(order);

	}

	@Test(expected=OrderException.class)
	public void placeOrder_quantity_zero() throws OrderException {
		OrderedItemsDto orderedItemsDto = new OrderedItemsDto(1L, 0);
		orderedItemsDto.setItemId(1L);
		orderedItemsDto.setQuantity(0);


		OrderRequestDto orderRequest = new OrderRequestDto();
		orderRequest.setCustomerId(1L);
		orderRequest.setRestaurantId(1L);
		orderRequest.setItems(Arrays.asList(orderedItemsDto));
		RestaurantFetchDto restaurant = new RestaurantFetchDto(1L, "Vasudev", "RR Nagar", "South Indian", 400, 4.2);
		MenuFetchDto menu = new MenuFetchDto(1L, "10", "22", restaurant);
		ItemFetchDto item = new ItemFetchDto(1L, "Onion Dosa", "Dosa", 110, menu);


		when(orderRepository.save(any(Order.class)))
		.thenReturn(new Order(orderRequest.getCustomerId(), "CREATED", orderRequest.getRestaurantId()));

		when(restTemplate.getForObject(any(String.class),any(Class.class))).thenReturn(item);
		//		when(itemService.saveItem(any(OrderedItem.class))).thenReturn(new OrderedItem());
		Order order = orderService.placeOrder(orderRequest);
		assertNotNull(order);

	}

	@Test
	public void cancelOrder_basic() {
		Optional<Order> order = Optional.of(new Order(1L, "CREATED", 2L));
		Order placedOrder = new Order();
		placedOrder.setCustomerId(1L);
		placedOrder.setId(1L);
		placedOrder.setStatus("CANCELLED");
		when(orderRepository.findById(anyLong())).thenReturn(order);
		when(orderRepository.save(any(Order.class))).thenReturn(placedOrder);
		Boolean isCancelled=orderService.cancelOrder(1L);
		assertTrue(isCancelled);
		assertEquals("CANCELLED", placedOrder.getStatus());

	}

	@Test
	public void cancelOrder_false() {
		Optional<Order> order = Optional.empty();
		when(orderRepository.findById(anyLong())).thenReturn(order);

		//		when(orderRepository.save(any(Order.class))).thenReturn(placedOrder);
		Boolean isCancelled=orderService.cancelOrder(1L);
		assertFalse(isCancelled);	
	}

	@Test
	public void getOrderById_basic() {
		Optional<Order> order = Optional.of(new Order(1L, "CREATED", 2L));
		when(orderRepository.findById(anyLong())).thenReturn(order);
		Optional<Order> orderReturned = orderService.getOrderById(1L);
		assertEquals("CREATED", orderReturned.get().getStatus());
	}

	@Test
	public void getOrderAmountByOrderId() {
		Optional<Order> order = Optional.of(new Order(1L, "CREATED", 2L));
		List<OrderedItem> itemsOrdered= new ArrayList<OrderedItem>();
		OrderedItem orderedItem1 = new OrderedItem("Dosa", 2, 100, order.get(), 1L);
		OrderedItem orderedItem2 = new OrderedItem("Idly", 2, 50, order.get(), 2L);
		itemsOrdered.add(orderedItem1);
		itemsOrdered.add(orderedItem2);
		when(orderRepository.findById(anyLong())).thenReturn(order);
		when(itemService.findbyOrderId(anyLong())).thenReturn(itemsOrdered);
		double totalAmount = orderService.getOrderAmountByOrderId(1L);
		assertEquals(300, totalAmount, 0.0);
	}

	@Test
	public void getOrderAmountByOrderId_zero() {
		Optional<Order> order = Optional.empty();
		List<OrderedItem> itemsOrdered= new ArrayList<OrderedItem>();
		itemsOrdered.add(new OrderedItem());

		when(orderRepository.findById(anyLong())).thenReturn(order);
		//		when(itemService.findbyOrderId(anyLong())).thenReturn(itemsOrdered);
		double totalAmount = orderService.getOrderAmountByOrderId(1L);
		assertEquals(0, totalAmount, 0.0);
	}

	@Test(expected = OrderException.class)
	public void updateOrder_orderNotFound() throws OrderException {
		Optional<Order> order = Optional.empty();
		when(orderRepository.findById(anyLong())).thenReturn(order);
		OrderUpdateResponseDto dto = new OrderUpdateResponseDto();
		dto.setCustomerId(1L);
		dto.setOrderedItems(new ArrayList<OrderedItem>());
		dto.setOrderId(1L);
		dto.setRestaurantId(1L);
		dto.setStatus("UPDATED");
		 orderService.updateOrder(new OrderUpdateDto(1L, 1L, Arrays.asList(new OrderedItemsDto(1L,2)), 1L));
		
	}

	@Test(expected = OrderException.class)
	public void updateOrder_quantityLess() throws OrderException {
		Optional<Order> order = Optional.of(new Order(1L, "CREATED", 1L));

		when(orderRepository.findById(anyLong())).thenReturn(order);

		RestaurantFetchDto restaurant = new RestaurantFetchDto(1L, "Vasudev", "RR Nagar", "South Indian", 400, 4.2);
		MenuFetchDto menu = new MenuFetchDto(1L, "10", "22", restaurant);
		ItemFetchDto item = new ItemFetchDto(1L, "Onion Dosa", "Dosa", 110, menu);

		when(restTemplate.getForObject(any(String.class),any(Class.class))).thenReturn(item);
		orderService.updateOrder(new OrderUpdateDto(1L, 1L, Arrays.asList(new OrderedItemsDto(1L, 0)), 1L));

	}

	@Test(expected = OrderException.class)
	public void updateOrder_itemnull() throws OrderException {
		Optional<Order> order = Optional.of(new Order(1L, "CREATED", 1L));

		when(orderRepository.findById(anyLong())).thenReturn(order);

		RestaurantFetchDto restaurant = new RestaurantFetchDto(1L, "Vasudev", "RR Nagar", "South Indian", 400, 4.2);
		MenuFetchDto menu = new MenuFetchDto(1L, "10", "22", restaurant);
		ItemFetchDto item = new ItemFetchDto(1L, "Onion Dosa", "Dosa", 110, menu);
		orderService.updateOrder(new OrderUpdateDto(1L, 1L, Arrays.asList(new OrderedItemsDto(1L, 1)), 1L));

	}

	@Test(expected = OrderException.class)
	public void updateOrder_differentRestaurantUpdate() throws OrderException {
		Optional<Order> order = Optional.of(new Order(1L, "CREATED", 1L));

		when(orderRepository.findById(anyLong())).thenReturn(order);

		RestaurantFetchDto restaurant = new RestaurantFetchDto(2L, "Vasudev", "RR Nagar", "South Indian", 400, 4.2);
		MenuFetchDto menu = new MenuFetchDto(1L, "10", "22", restaurant);
		ItemFetchDto item = new ItemFetchDto(1L, "Onion Dosa", "Dosa", 110, menu);

		when(restTemplate.getForObject(any(String.class),any(Class.class))).thenReturn(item);
		orderService.updateOrder(new OrderUpdateDto(1L, 1L, Arrays.asList(new OrderedItemsDto(1L, 1)), 1L));

	}

	@Test
	public void updateOrder_basic() throws OrderException {
		Order orderReturned = new Order(1L, "UPDATED", 1L);
		orderReturned.setId(1L);
		Optional<Order> order = Optional.of(new Order(1L, "CREATED", 1L));

		when(orderRepository.findById(anyLong())).thenReturn(order);

		RestaurantFetchDto restaurant = new RestaurantFetchDto(1L, "Vasudev", "RR Nagar", "South Indian", 400, 4.2);
		MenuFetchDto menu = new MenuFetchDto(1L, "10", "22", restaurant);
		ItemFetchDto item = new ItemFetchDto(1L, "Onion Dosa", "Dosa", 110, menu);

		when(restTemplate.getForObject(any(String.class),any(Class.class))).thenReturn(item);
		when( itemService.saveItem(any(OrderedItem.class))).thenReturn(new OrderedItem("Onion Dosa", 1, 110, orderReturned, 1L));
		when(orderRepository.save(any(Order.class))).thenReturn(orderReturned);
	
		OrderUpdateResponseDto response = orderService.updateOrder(new OrderUpdateDto(1L,1L,Arrays.asList(new OrderedItemsDto(1L, 1)),1L));
		assertEquals("UPDATED", response.getStatus());
	}
	
	@Test(expected=OrderException.class)
	public void updateOrder_serviceunavailable() throws OrderException {
		Order orderReturned = new Order(1L, "UPDATED", 1L);
		orderReturned.setId(1L);
		Optional<Order> order = Optional.of(new Order(1L, "CREATED", 1L));

		when(orderRepository.findById(anyLong())).thenReturn(order);

		RestaurantFetchDto restaurant = new RestaurantFetchDto(1L, "Vasudev", "RR Nagar", "South Indian", 400, 4.2);
		MenuFetchDto menu = new MenuFetchDto(1L, "10", "22", restaurant);
		OrderUpdateResponseDto response = orderService.updateOrder(new OrderUpdateDto(1L,1L,Arrays.asList(new OrderedItemsDto(1L, 1)),1L));
		assertEquals("UPDATED", response.getStatus());
	}
	
	@Test(expected = OrderException.class)
	public void updateOrder_restaurantDifferent() throws OrderException {
		Optional<Order> order = Optional.of(new Order(1L, "CREATED", 1L));

		when(orderRepository.findById(anyLong())).thenReturn(order);

		RestaurantFetchDto restaurant = new RestaurantFetchDto(1L, "Vasudev", "RR Nagar", "South Indian", 400, 4.2);
		MenuFetchDto menu = new MenuFetchDto(1L, "10", "22", restaurant);
		ItemFetchDto item = new ItemFetchDto(1L, "Onion Dosa", "Dosa", 110, menu);

//		when(restTemplate.getForObject(any(String.class),any(Class.class))).thenReturn(item);
		orderService.updateOrder(new OrderUpdateDto(1L, 2L, Arrays.asList(new OrderedItemsDto(1L, 1)), 1L));
	}
}
