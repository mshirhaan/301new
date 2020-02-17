package com.eatza.order.service.orderservice;

import java.util.Optional;

import com.eatza.order.dto.OrderRequestDto;
import com.eatza.order.dto.OrderUpdateDto;
import com.eatza.order.dto.OrderUpdateResponseDto;
import com.eatza.order.exception.OrderException;
import com.eatza.order.model.Order;

public interface OrderService  {
	
	Order placeOrder(OrderRequestDto orderRequest) throws OrderException;
	boolean cancelOrder(Long orderId);
	Optional<Order> getOrderById(Long id);
	double getOrderAmountByOrderId(Long id);
	OrderUpdateResponseDto updateOrder(OrderUpdateDto orderUpdateRequest) throws OrderException;

}
