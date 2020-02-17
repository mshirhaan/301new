package com.eatza.restaurantsearch.service.restaurantservice;

import java.util.List;

import com.eatza.restaurantsearch.dto.RestaurantRequestDto;
import com.eatza.restaurantsearch.dto.RestaurantResponseDto;
import com.eatza.restaurantsearch.model.MenuItem;
import com.eatza.restaurantsearch.model.Restaurant;

public interface RestaurantService {
	
	RestaurantResponseDto findAllRestaurants(int pageNumber, int pageSize);
	Restaurant saveRestaurant(RestaurantRequestDto restaurant);
	
	RestaurantResponseDto findByName(String name, int pageNumber, int pageSize);
	RestaurantResponseDto findByLocationAndCuisine(String location, String cuisine, int pageNumber, int pageSize);
	RestaurantResponseDto findByBudget(int budget, int pageNumber, int pageSize);
	RestaurantResponseDto findByLocationAndName(String location, String name, int pageNumber, int pageSize);
	RestaurantResponseDto findByRating(double rating,int pageNumber, int pageSize);
	Restaurant findById(Long id)  ;
	List<MenuItem> findMenuItemByRestaurantId(Long restaurantId, int pageNumber, int pageSize);

}
