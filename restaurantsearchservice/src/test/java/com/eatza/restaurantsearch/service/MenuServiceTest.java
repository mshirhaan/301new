package com.eatza.restaurantsearch.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static  org.mockito.ArgumentMatchers.any;
import static  org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import com.eatza.restaurantsearch.model.Menu;
import com.eatza.restaurantsearch.model.Restaurant;
import com.eatza.restaurantsearch.repository.MenuRepository;
import com.eatza.restaurantsearch.service.menuservice.MenuServiceImpl;


@RunWith(SpringRunner.class)
public class MenuServiceTest {
	
	
	@InjectMocks
	private MenuServiceImpl menuService;
	
	@Mock
	private MenuRepository menuRepository;
	
	@Test
	public void getMenuByRestaurantId() {
		Menu menu = new Menu("10", "22", new Restaurant("Dominos", "RR", "Italian", 300, 4.2));
		when(menuRepository.findByRestaurant_id(anyLong())).thenReturn(menu);
		Menu returnedMenu = menuService.getMenuByRestaurantId(1L);
		assertEquals("Dominos", returnedMenu.getRestaurant().getName());
	}
	
	@Test
	public void getMenuById() {
		Menu menu = new Menu("10", "22", new Restaurant("Dominos", "RR", "Italian", 300, 4.2));
		Optional<Menu> optional= Optional.of(menu);
		when(menuRepository.findById(anyLong())).thenReturn(optional);
		Optional<Menu> returnedMenu= menuService.getMenuById(1L);
		assertTrue(returnedMenu.isPresent());
	}
	
	@Test
	public void saveMenu() {
		Menu menu = new Menu();
		menu.setActiveFrom("10");
		menu.setActiveTill("22");
		menu.setId(1L);
		Restaurant restaurant = new Restaurant();
		restaurant.setBudget(300);
		restaurant.setLocation("RR");
		restaurant.setCuisine("Italian");
		restaurant.setRating(4.2);
		restaurant.setName("Dominos");
		menu.setRestaurant(restaurant);

		
		when(menuRepository.save(any(Menu.class))).thenReturn(menu);
		Menu returnedMenu= menuService.saveMenu(menu);
		assertEquals("10", returnedMenu.getActiveFrom());
	}

}
