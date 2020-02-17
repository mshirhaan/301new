package com.eatza.restaurantsearch.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static  org.mockito.ArgumentMatchers.any;
import static  org.mockito.ArgumentMatchers.anyDouble;
import static  org.mockito.ArgumentMatchers.anyInt;
import static  org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import com.eatza.restaurantsearch.dto.RestaurantRequestDto;
import com.eatza.restaurantsearch.dto.RestaurantResponseDto;
import com.eatza.restaurantsearch.exception.RestaurantNotFoundException;
import com.eatza.restaurantsearch.model.Menu;
import com.eatza.restaurantsearch.model.MenuItem;
import com.eatza.restaurantsearch.model.Restaurant;
import com.eatza.restaurantsearch.repository.RestaurantRepository;
import com.eatza.restaurantsearch.service.menuitemservice.MenuItemService;
import com.eatza.restaurantsearch.service.menuservice.MenuService;
import com.eatza.restaurantsearch.service.restaurantservice.RestaurantServiceImpl;

@RunWith(SpringRunner.class)
public class RestaurantServiceTest {


	@InjectMocks
	RestaurantServiceImpl restaurantService;

	@Mock
	RestaurantRepository restaurantRepository;

	@Mock
	MenuService menuService;
	
	@Mock
	MenuItemService menuItemService;


	@Test
	public void saveRestaurant() {
		RestaurantRequestDto restaurantDto = new RestaurantRequestDto();
		restaurantDto.setName("Dominos");
		restaurantDto.setActiveFrom("11");
		restaurantDto.setActiveTill("22");
		restaurantDto.setBudget(400);
		restaurantDto.setCuisine("Italian");
		restaurantDto.setLocation("RR Nagar");
		restaurantDto.setRating(5);
		Restaurant restaurant = new Restaurant(restaurantDto.getName(), restaurantDto.getLocation(),
				restaurantDto.getCuisine(), restaurantDto.getBudget(), restaurantDto.getRating());

		when(restaurantRepository.save(any(Restaurant.class))).thenReturn(restaurant);	

		Restaurant persistedRestaurant = restaurantService.saveRestaurant(restaurantDto);
		Menu menu = new Menu(restaurantDto.getActiveFrom(), restaurantDto.getActiveTill(), persistedRestaurant);
		when(menuService.saveMenu(any(Menu.class))).thenReturn(menu);
		assertNotNull(persistedRestaurant);
		assertEquals("Dominos",persistedRestaurant.getName());
		assertEquals(400,persistedRestaurant.getBudget());
		assertEquals("Italian",persistedRestaurant.getCuisine());
		assertEquals("RR Nagar",persistedRestaurant.getLocation());

	}

	@Test
	public void getAllRestaurants() {
		int pageNumber=1;
		int pageSize=10;		
		Page<Restaurant> page = mock(Page.class);
		when(page.getContent()).thenReturn(Arrays
				.asList(new Restaurant("Dominos", "RR Nagar", "Italian", 400, 4.2)
						, new Restaurant("RR Vatika", "RR Nagar", "North Indian", 200, 4.1)));
		when(page.getTotalPages()).thenReturn(2);
		when(page.getTotalElements()).thenReturn(10L);
		when(restaurantRepository.findAll(any(Pageable.class))).thenReturn(page);
		RestaurantResponseDto dto = restaurantService.findAllRestaurants(pageNumber, pageSize);
		assertEquals("Dominos", dto.getRestaurants().get(0).getName());



	}
	
	@Test
	public void findByName() {
		int pageNumber=1;
		int pageSize=10;		
		Page<Restaurant> page = mock(Page.class);
		when(page.getContent()).thenReturn(Arrays
				.asList(new Restaurant("Dominos", "RR Nagar", "Italian", 400, 4.2)
						, new Restaurant("RR Vatika", "RR Nagar", "North Indian", 200, 4.1)));
		when(page.getTotalPages()).thenReturn(2);
		when(page.getTotalElements()).thenReturn(10L);
		when(restaurantRepository.findByNameContaining(any(String.class),any(Pageable.class))).thenReturn(page);
		RestaurantResponseDto dto = restaurantService.findByName("Dominos", pageNumber, pageSize);
		assertEquals("Dominos", dto.getRestaurants().get(0).getName());


	}
	
	
	@Test
	public void findByLocationAndCuisine() {
		int pageNumber=1;
		int pageSize=10;		
		Page<Restaurant> page = mock(Page.class);
		when(page.getContent()).thenReturn(Arrays
				.asList(new Restaurant("Dominos", "RR Nagar", "Italian", 400, 4.2)
						, new Restaurant("RR Vatika", "RR Nagar", "North Indian", 200, 4.1)));
		when(page.getTotalPages()).thenReturn(2);
		when(page.getTotalElements()).thenReturn(10L);
		when(restaurantRepository.findByLocationContainingAndCuisineContaining(any(String.class),any(String.class),any(Pageable.class))).thenReturn(page);
		when(restaurantRepository.findByLocationContainingOrCuisineContaining(any(String.class),any(String.class),any(Pageable.class))).thenReturn(page);
		RestaurantResponseDto dto = restaurantService.findByLocationAndCuisine("RR Nagar", "Italian", pageNumber, pageSize);
		assertEquals("Dominos", dto.getRestaurants().get(0).getName());


	}

	@Test
	public void findByLocationAndName() {
		int pageNumber=1;
		int pageSize=10;		
		Page<Restaurant> page = mock(Page.class);
		when(page.getContent()).thenReturn(Arrays
				.asList(new Restaurant("Dominos", "RR Nagar", "Italian", 400, 4.2)
						, new Restaurant("RR Vatika", "RR Nagar", "North Indian", 200, 4.1)));
		when(page.getTotalPages()).thenReturn(2);
		when(page.getTotalElements()).thenReturn(10L);
		when(restaurantRepository.findByLocationContainingAndNameContaining(any(String.class),any(String.class),any(Pageable.class))).thenReturn(page);
		when(restaurantRepository.findByLocationContainingAndNameContaining(any(String.class),any(String.class),any(Pageable.class))).thenReturn(page);
		RestaurantResponseDto dto = restaurantService.findByLocationAndName("RR Nagar", "Dominos", pageNumber, pageSize);
		assertEquals("Dominos", dto.getRestaurants().get(0).getName());


	}
	
	@Test
	public void findByBudget() {
		int pageNumber=1;
		int pageSize=10;		
		Page<Restaurant> page = mock(Page.class);
		when(page.getContent()).thenReturn(Arrays
				.asList(new Restaurant("Dominos", "RR Nagar", "Italian", 400, 4.2)
						, new Restaurant("RR Vatika", "RR Nagar", "North Indian", 200, 4.1)));
		when(page.getTotalPages()).thenReturn(2);
		when(page.getTotalElements()).thenReturn(10L);
		when(restaurantRepository.findByBudgetLessThanEqual(anyInt(),any(Pageable.class))).thenReturn(page);
		RestaurantResponseDto dto = restaurantService.findByBudget(400, pageNumber, pageSize);
		RestaurantResponseDto dtoExpected= new RestaurantResponseDto();
		dtoExpected.setRestaurants(Arrays
				.asList(new Restaurant("Dominos", "RR Nagar", "Italian", 400, 4.2)
						, new Restaurant("RR Vatika", "RR Nagar", "North Indian", 200, 4.1)));
		dtoExpected.setTotalElements(1);
		dtoExpected.setTotalPages(1);
		assertEquals("Dominos", dto.getRestaurants().get(0).getName());


	}
	
	@Test
	public void findByRating() {
		int pageNumber=1;
		int pageSize=10;		
		Page<Restaurant> page = mock(Page.class);
		when(page.getContent()).thenReturn(Arrays
				.asList(new Restaurant("Dominos", "RR Nagar", "Italian", 400, 4.2)
						, new Restaurant("RR Vatika", "RR Nagar", "North Indian", 200, 4.1)));
		when(page.getTotalPages()).thenReturn(2);
		when(page.getTotalElements()).thenReturn(10L);
		when(restaurantRepository.findByRatingGreaterThanEqual(anyDouble(),any(Pageable.class))).thenReturn(page);
		RestaurantResponseDto dto = restaurantService.findByRating(4, pageNumber, pageSize);
		assertEquals("Dominos", dto.getRestaurants().get(0).getName());
	}
	
	@Test
	public void findById() {
		when(restaurantRepository.findById(anyLong()))
			.thenReturn(Optional
					.of(new Restaurant("Dominos", "RR Nagar", "Italian", 400, 4.2)));
		
		Restaurant rest = restaurantService.findById(1L);
		assertEquals("Dominos", rest.getName());
	}
	
	@Test(expected = RestaurantNotFoundException.class)
	public void findById_empty() {
		when(restaurantRepository.findById(anyLong()))
			.thenReturn(Optional.empty());
		
		Restaurant rest = restaurantService.findById(1L);
	}
	
	
	@Test
	public void findMenuItemByRestaurantId_basic() {
		List<MenuItem> menuItems = Arrays.asList(
				new MenuItem("Dosa", "Dosa", 50, new Menu("Active", "Till", new Restaurant("Dominos", "RR Nagar", "Italian", 400, 4.2))));
		Page<MenuItem> page = new PageImpl<>(menuItems);
		
		when(menuService.getMenuByRestaurantId(anyLong()))
			.thenReturn(new Menu("From", "Till", new Restaurant("RR Vatika", "RR Nagar", "North Indian", 200, 4.1)));
		when(menuItemService.findByMenuId(any(),any())).thenReturn(page);
		
		
		List<MenuItem> menuItems2 = restaurantService.findMenuItemByRestaurantId(1L, 1, 10);
		assertEquals("Dosa", menuItems2.get(0).getName());
		
	}
	@Test(expected=RestaurantNotFoundException.class)
	public void findMenuItemByRestaurantId_expection() {
		List<MenuItem> menuItems = new ArrayList<>();;
		Page<MenuItem> page = new PageImpl<>(menuItems);
		
		when(menuService.getMenuByRestaurantId(anyLong()))
			.thenReturn(new Menu("From", "Till", new Restaurant("RR Vatika", "RR Nagar", "North Indian", 200, 4.1)));
		when(menuItemService.findByMenuId(any(),any())).thenReturn(page);
		
		
		List<MenuItem> menuItems2 = restaurantService.findMenuItemByRestaurantId(1L, 1, 10);
		assertEquals("Dosa", menuItems2.get(0).getName());
		
	}
}
