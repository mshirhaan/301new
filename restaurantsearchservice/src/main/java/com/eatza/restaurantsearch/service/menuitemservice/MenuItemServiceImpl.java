package com.eatza.restaurantsearch.service.menuitemservice;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.eatza.restaurantsearch.dto.ItemRequestDto;
import com.eatza.restaurantsearch.exception.ItemNotFoundException;
import com.eatza.restaurantsearch.exception.MenuNotSavedException;
import com.eatza.restaurantsearch.exception.RestaurantBadRequestException;
import com.eatza.restaurantsearch.model.Menu;
import com.eatza.restaurantsearch.model.MenuItem;
import com.eatza.restaurantsearch.model.Restaurant;
import com.eatza.restaurantsearch.repository.MenuItemRepository;
import com.eatza.restaurantsearch.service.menuservice.MenuService;
import com.eatza.restaurantsearch.service.restaurantservice.RestaurantService;

@Service
public class MenuItemServiceImpl implements MenuItemService {
	
	private static final Logger logger = LoggerFactory.getLogger(MenuItemServiceImpl.class);

	
	@Autowired
	private MenuItemRepository menuItemRepository;

	@Autowired
	private MenuService menuService;

	@Autowired
	private RestaurantService restaurantService;

	@Override
	public MenuItem saveMenuItem(ItemRequestDto itemDto) {
		logger.debug("In save menu Item method, calling repo");

		Optional<Menu> menu =  menuService.getMenuById(itemDto.getMenuId());
		if(menu.isPresent()) {
			logger.debug("Found correponding menu, saving menu item");
		MenuItem menuItem = new MenuItem(itemDto.getName(), itemDto.getDescription(), itemDto.getPrice(), menu.get());
		return menuItemRepository.save(menuItem);
		}
		else {
			logger.debug("Crreponding menu not found");
			throw new MenuNotSavedException("Menu not saved, something went wrong");
		}
	}

	@Override
	@Cacheable(value="menuitemsbyname")
	public List<Restaurant> findByName(String name, int pagenumber, int pagesize) throws ItemNotFoundException   {
		
		logger.debug("In findByName, creating pageable"
				+ " object for page Number:"+pagenumber+" and page size: "+ pagesize);
		
		Pageable pageable = PageRequest.of(pagenumber-1, pagesize);
		logger.debug("Calling repo to find menu items");
		Page<MenuItem> menuItems =  menuItemRepository.findByNameContaining(name, pageable);
		if(menuItems.hasContent()) {
		List<Restaurant> restaurantsToReturn = new ArrayList<>();
		for(MenuItem item: menuItems) {
		
			
			
			Optional<Menu> menu = menuService.getMenuById(item.getMenu().getId());
			if(menu.isPresent()) {
				logger.debug("Menu is present calling restaurant service to find restaurant by id");
				restaurantsToReturn.add(restaurantService.findById(menu.get().getRestaurant().getId()));
			}
			else {
				logger.debug("No restaurants found, something went wrong");
					throw new RestaurantBadRequestException("No restaurants found, something went wrong");
			
			}
			if(restaurantsToReturn.size()>=pagesize) {
				
				break;
			}

		}
		return restaurantsToReturn;
		}
		else {
			logger.debug("Items given are not present in any restaurant");
			throw new ItemNotFoundException("Items given are not present in any restaurant");
		}


	}

	@Override
	@Cacheable(value="menuitems")
	public Optional<MenuItem> findById(Long id) {
		return menuItemRepository.findById(id);
	}

	@Override
	@Cacheable(value="menuitemsbymenuid")
	public Page<MenuItem> findByMenuId(Long id, Pageable pageable) {
		logger.debug("In findByMenuId, calling repository");
		return menuItemRepository.findByMenu_id(id, pageable);
	}

}
