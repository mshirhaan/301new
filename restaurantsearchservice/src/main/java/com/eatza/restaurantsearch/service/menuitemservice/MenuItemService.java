package com.eatza.restaurantsearch.service.menuitemservice;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.eatza.restaurantsearch.dto.ItemRequestDto;
import com.eatza.restaurantsearch.exception.ItemNotFoundException;
import com.eatza.restaurantsearch.model.MenuItem;
import com.eatza.restaurantsearch.model.Restaurant;


public interface MenuItemService {
	
	MenuItem saveMenuItem(ItemRequestDto itemDto);
	List<Restaurant> findByName(String name, int pagenumber, int pagesize) throws  ItemNotFoundException ;
	Optional<MenuItem> findById(Long id);
	Page<MenuItem> findByMenuId(Long id , Pageable pageable);

}
