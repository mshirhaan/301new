package com.eatza.order.service.itemservice;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eatza.order.model.OrderedItem;
import com.eatza.order.repository.OrderedItemRepository;

@Service
public class ItemServiceImpl implements ItemService {
	
	
	private static final Logger logger = LoggerFactory.getLogger(ItemServiceImpl.class);

	@Autowired
	OrderedItemRepository itemRepository;
	
	public OrderedItem saveItem(OrderedItem item) {
		return itemRepository.save(item);
	}

	@Override
	public List<OrderedItem> findbyOrderId(Long id) {
		logger.debug("In find item by order id method in service, calling repository to fetch");

		return itemRepository.findbyOrderId(id);
	}
	@Override
	public void deleteItemsById(Long id) {
		logger.debug("In delete item by id method, calling repository");
		itemRepository.deleteById(id);
	}


}
