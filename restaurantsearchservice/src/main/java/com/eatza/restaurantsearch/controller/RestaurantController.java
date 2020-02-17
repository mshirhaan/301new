package com.eatza.restaurantsearch.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eatza.restaurantsearch.dto.RestaurantRequestDto;
import com.eatza.restaurantsearch.dto.RestaurantResponseDto;
import com.eatza.restaurantsearch.exception.RestaurantBadRequestException;
import com.eatza.restaurantsearch.exception.RestaurantNotFoundException;
import com.eatza.restaurantsearch.model.MenuItem;
import com.eatza.restaurantsearch.service.restaurantservice.RestaurantService;



@RestController
public class RestaurantController {

	@Autowired
	private RestaurantService restaurantService;
	
	private static final Logger logger = LoggerFactory.getLogger(RestaurantController.class);

	private static final String RESTAURANT_BAD_REQUEST_MSG="Page number or Page size cannot be 0 or less";
	private static final String RESTAURANT_NOT_FOUND_MSG="No Restaurants found for specified inputs";

	@GetMapping("/restaurants")
	public ResponseEntity<RestaurantResponseDto> getAllRestaurants(@RequestHeader String authorization,@RequestParam(defaultValue="1") int pagenumber,@RequestParam(defaultValue="10") int pagesize) {
		logger.debug("In getall restaurants method");
		if(pagenumber<=0 || pagesize<=0) {
			logger.debug("Page number or size cannot be zero or less, throwing exception");
			throw new RestaurantBadRequestException(RESTAURANT_BAD_REQUEST_MSG);
		}
		logger.debug("calling service to get restaurants with pagination");
		RestaurantResponseDto responseDto = restaurantService.findAllRestaurants(pagenumber,pagesize);
		if(responseDto.getRestaurants().isEmpty()) {
			logger.debug("No restaurants were found");
			throw new RestaurantNotFoundException(RESTAURANT_NOT_FOUND_MSG);
		}
		
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(responseDto);



	}

	@PostMapping("/restaurant")
	public ResponseEntity<String> addRestaurant(@RequestHeader String authorization,@RequestBody RestaurantRequestDto restaurantDto){
		
		logger.debug("In add restaurants method, calling service");

		restaurantService.saveRestaurant(restaurantDto);
		logger.debug("Restaurant saved, returning back");

		return ResponseEntity
				.status(HttpStatus.OK)
				.body("Restaurant Added successfully");


	}


	@GetMapping("/restaurants/name/{name}")
	public ResponseEntity<RestaurantResponseDto> getRestaurantsByName(@RequestHeader String authorization,@PathVariable String name,@RequestParam(defaultValue="1") int pagenumber,@RequestParam(defaultValue="10") int pagesize) {
		logger.debug("In get restaurants by name method");
		if(pagenumber<=0 || pagesize<=0) {
			logger.debug("Page number or size cannot be zero or less, throwing exception");

			throw new RestaurantBadRequestException(RESTAURANT_BAD_REQUEST_MSG);
		}
		RestaurantResponseDto responseDto =  restaurantService.findByName(name, pagenumber, pagesize);
		if(responseDto.getRestaurants().isEmpty()) {
			logger.debug("No restaurants were found");

			throw new RestaurantNotFoundException(RESTAURANT_NOT_FOUND_MSG);
		}
		
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(responseDto);
	}

	@GetMapping("/restaurants/location/{location}/cuisine/{cuisine}")
	public ResponseEntity<RestaurantResponseDto> getRestaurantsByLocationCuisine(@RequestHeader String authorization,@PathVariable String location, @PathVariable String cuisine, @RequestParam(defaultValue="1") int pagenumber,@RequestParam(defaultValue="10") int pagesize) {
		logger.debug("In get restaurants by location and cuisine method");
		if(pagenumber<=0 || pagesize<=0) {
			logger.debug("Page number or size cannot be zero or less, throwing exception");
			throw new RestaurantBadRequestException(RESTAURANT_BAD_REQUEST_MSG);
		}
		RestaurantResponseDto responseDto = restaurantService.findByLocationAndCuisine(location, cuisine, pagenumber, pagesize);
		if(responseDto.getRestaurants().isEmpty())
		{
			logger.debug("No restaurants were found");
			throw new RestaurantNotFoundException(RESTAURANT_NOT_FOUND_MSG);
		}
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(responseDto);

	}

	@GetMapping("/restaurants/name/{name}/location/{location}")
	public ResponseEntity<RestaurantResponseDto> getRestaurantsByLocationName(@RequestHeader String authorization,@PathVariable String location, @PathVariable String name, @RequestParam(defaultValue="1") int pagenumber,@RequestParam(defaultValue="10") int pagesize) {
		logger.debug("In get restaurants by location and cuisine method");

		if(pagenumber<=0 || pagesize<=0) {
			logger.debug("Page number or size cannot be zero or less, throwing exception");
			throw new RestaurantBadRequestException(RESTAURANT_BAD_REQUEST_MSG);
		}
		RestaurantResponseDto responseDto= restaurantService.findByLocationAndName(location, name, pagenumber, pagesize);
		if(responseDto.getRestaurants().isEmpty()) {
			logger.debug("No restaurants were found");
			throw new RestaurantNotFoundException(RESTAURANT_NOT_FOUND_MSG);
		}
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(responseDto);

	}

	@GetMapping("/restaurants/budget/{budget}")
	public ResponseEntity<RestaurantResponseDto> getRestaurantsByBudget(@RequestHeader String authorization,@PathVariable int budget ,@RequestParam(defaultValue="1") int pagenumber,@RequestParam(defaultValue="10") int pagesize) {
		logger.debug("In get restaurants by budget method");

		if(pagenumber<=0 || pagesize<=0) {
			logger.debug("Page number or size cannot be zero or less, throwing exception");
			throw new RestaurantBadRequestException(RESTAURANT_BAD_REQUEST_MSG);
		}
		RestaurantResponseDto responseDto= restaurantService.findByBudget(budget, pagenumber, pagesize);
		if(responseDto.getRestaurants().isEmpty()) {
			logger.debug("No restaurants were found");
			throw new RestaurantNotFoundException(RESTAURANT_NOT_FOUND_MSG);
		}
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(responseDto);

	}

	@GetMapping("/restaurants/rating/{rating}")
	public ResponseEntity<RestaurantResponseDto> getRestaurantsByRating(@RequestHeader String authorization,@PathVariable double rating, @RequestParam(defaultValue="1") int pagenumber,@RequestParam(defaultValue="10") int pagesize) {
		logger.debug("In get restaurants by rating method");

		if(pagenumber<=0 || pagesize<=0) {
			logger.debug("Page number or size cannot be zero or less, throwing exception");
			throw new RestaurantBadRequestException(RESTAURANT_BAD_REQUEST_MSG);
		}
		RestaurantResponseDto responseDto = restaurantService.findByRating(rating, pagenumber, pagesize);
		if(responseDto.getRestaurants().isEmpty()) {
			logger.debug("No restaurants were found");
			throw new RestaurantNotFoundException(RESTAURANT_NOT_FOUND_MSG);
		}
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(responseDto);

	}
	
	@GetMapping("/restaurant/items/{restaurantid}")
	public ResponseEntity<List<MenuItem>> getItemsByRestaurantId(@RequestHeader String authorization,@PathVariable Long restaurantid, @RequestParam(defaultValue="1") int pagenumber,@RequestParam(defaultValue="10") int pagesize) {

		List<MenuItem> items = restaurantService.findMenuItemByRestaurantId(restaurantid, pagenumber, pagesize);
		if(items.isEmpty()) {
			throw new RestaurantNotFoundException(RESTAURANT_NOT_FOUND_MSG);
		}
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(items);

	}
}