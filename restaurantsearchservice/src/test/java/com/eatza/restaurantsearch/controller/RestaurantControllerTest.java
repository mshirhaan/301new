package com.eatza.restaurantsearch.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.eatza.restaurantsearch.dto.RestaurantRequestDto;
import com.eatza.restaurantsearch.dto.RestaurantResponseDto;
import com.eatza.restaurantsearch.exception.InvalidTokenException;
import com.eatza.restaurantsearch.model.Menu;
import com.eatza.restaurantsearch.model.MenuItem;
import com.eatza.restaurantsearch.model.Restaurant;
import com.eatza.restaurantsearch.service.restaurantservice.RestaurantService;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


@RunWith(SpringRunner.class)
@WebMvcTest(value= RestaurantController.class)
public class RestaurantControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private RestaurantService restaurantService;

	@Autowired
	private ObjectMapper objectMapper;
	

	Restaurant restaurant;
	List<Restaurant> restaurants;
	String jwt="";
	private static final long EXPIRATIONTIME = 900000;
	@Before
	public void setup() {
		jwt = "Bearer "+Jwts.builder().setSubject("user").claim("roles", "user").setIssuedAt(new Date())
				.signWith(SignatureAlgorithm.HS256, "secretkey").setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME)).compact();
	}


	// Passing
	@Test
	public void getAllRestaurants_basic() throws Exception {
		restaurants = new ArrayList<>();
		restaurant = new Restaurant("Dominos", "RR Nagar", "Italian", 400, 4.2);
		restaurant.setId((long) 1);
		restaurants.add(restaurant);
		RestaurantResponseDto responseDto = new RestaurantResponseDto(restaurants, 2, 20);
		Mockito.when(restaurantService.findAllRestaurants(any(Integer.class), 
				any(Integer.class))).thenReturn(responseDto);
		RequestBuilder request = MockMvcRequestBuilders.get(
				"/restaurants?pagenumber=1&pagesize=10").accept(
						MediaType.ALL)
				.header(HttpHeaders.AUTHORIZATION,
						jwt);

		mockMvc.perform(request)
		.andExpect(status().isOk())
		.andExpect(content().json("{restaurants:[{budget: 400, cuisine: Italian, id: 1, location: \"RR Nagar\", name: Dominos, rating: 4.2}], totalElements: 20, totalPages: 2}"))
		.andReturn();
	}

	// Passing
	@Test
	public void getAllRestaurants_zeroPage() throws Exception {
		RestaurantResponseDto responseDto = new RestaurantResponseDto(restaurants, 0, 0);
		Mockito.when(restaurantService.findAllRestaurants(any(Integer.class), 
				any(Integer.class))).thenReturn(responseDto);
		RequestBuilder request = MockMvcRequestBuilders.get(
				"/restaurants?pagenumber=0&pagesize=0").accept(
						MediaType.ALL)
				.header(HttpHeaders.AUTHORIZATION,
						jwt);

		mockMvc.perform(request)
		.andExpect(status().is(400))
		.andReturn();
	}


	// Passing
	@Test
	public void getAllRestaurants_empty() throws Exception {
		List<Restaurant> returnedRestaurants = new ArrayList<>();
		RestaurantResponseDto responseDto = new RestaurantResponseDto(returnedRestaurants, 1,1);
		Mockito.when(restaurantService.findAllRestaurants(any(Integer.class), 
				any(Integer.class))).thenReturn(responseDto);
		RequestBuilder request = MockMvcRequestBuilders.get(
				"/restaurants?pagenumber=1&pagesize=1").accept(
						MediaType.ALL)
				.header(HttpHeaders.AUTHORIZATION,
						jwt);

		mockMvc.perform(request)
		.andExpect(status().is(404))
		.andReturn();
	}

	@Test
	public void addRestaurant() throws Exception {
		RestaurantRequestDto requestDto = new RestaurantRequestDto();
		requestDto.setActiveFrom("10");
		requestDto.setActiveTill("22");
		requestDto.setBudget(400);
		requestDto.setCuisine("Spanish");
		requestDto.setLocation("MG Road");
		requestDto.setName("Sip n Bite");
		requestDto.setRating(4.3);
		when(restaurantService.saveRestaurant(any(RestaurantRequestDto.class))).thenReturn(new Restaurant());
		RequestBuilder request = MockMvcRequestBuilders.post(
				"/restaurant")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString((requestDto)))
				.header(HttpHeaders.AUTHORIZATION,
						jwt);
		mockMvc.perform(request)
		.andExpect(status().is(200))
		.andExpect(content().string("Restaurant Added successfully"))
		.andReturn();
	}

	@Test
	public void getRestaurantsByRating() throws Exception {
		// list of resturants with rating below, equal and above 4.3
		List<Restaurant> resturants = Arrays.asList(new Restaurant("Resturant1", "Location1", "Chinese", 400, 4.0)
				, new Restaurant("Resturant2", "Location2", "Indian", 200, 4.3)
				, new Restaurant("Resturant3", "Location3", "South Indian", 300, 4.6));

		RestaurantResponseDto responseDTO = new RestaurantResponseDto(resturants, 1, 10);
		when(restaurantService.findByRating(anyDouble(), anyInt(), anyInt())).thenReturn(responseDTO);

		// request
		RequestBuilder request = MockMvcRequestBuilders
				.get("/restaurants/rating/4.3?pagenumber=1&pagesize=10")
				.accept(MediaType.APPLICATION_JSON)
				.header(HttpHeaders.AUTHORIZATION,
						jwt);

		// response
		mockMvc.perform(request)
		.andExpect(status().isOk())
		//.andExpect(content().json("{restaurants:[{name: Resturant2, rating: 4.3}, {name: Resturant3, rating: 4.6}]}"))
		.andReturn();
	}
	@Test
	public void getRestaurantsByRating_zeropgaenumber() throws Exception {
		// list of resturants with rating below, equal and above 4.3
		List<Restaurant> resturants = Arrays.asList(new Restaurant("Resturant1", "Location1", "Chinese", 400, 4.0)
				, new Restaurant("Resturant2", "Location2", "Indian", 200, 4.3)
				, new Restaurant("Resturant3", "Location3", "South Indian", 300, 4.6));

		RestaurantResponseDto responseDTO = new RestaurantResponseDto(resturants, 1, 10);
		when(restaurantService.findByRating(anyDouble(), anyInt(), anyInt())).thenReturn(responseDTO);

		// request
		RequestBuilder request = MockMvcRequestBuilders
				.get("/restaurants/rating/4.3?pagenumber=0&pagesize=10")
				.accept(MediaType.APPLICATION_JSON)
				.header(HttpHeaders.AUTHORIZATION,
						jwt);

		// response
		mockMvc.perform(request)
		.andExpect(status().is(400))
		//.andExpect(content().json("{restaurants:[{name: Resturant2, rating: 4.3}, {name: Resturant3, rating: 4.6}]}"))
		.andReturn();
	}
	
	@Test
	public void getRestaurantsByRating_zero_pagesize() throws Exception {
		// list of resturants with rating below, equal and above 4.3
		List<Restaurant> resturants = Arrays.asList(new Restaurant("Resturant1", "Location1", "Chinese", 400, 4.0)
				, new Restaurant("Resturant2", "Location2", "Indian", 200, 4.3)
				, new Restaurant("Resturant3", "Location3", "South Indian", 300, 4.6));

		RestaurantResponseDto responseDTO = new RestaurantResponseDto(resturants, 1, 10);
		when(restaurantService.findByRating(anyDouble(), anyInt(), anyInt())).thenReturn(responseDTO);

		// request
		RequestBuilder request = MockMvcRequestBuilders
				.get("/restaurants/rating/4.3?pagenumber=1&pagesize=0")
				.accept(MediaType.APPLICATION_JSON)
				.header(HttpHeaders.AUTHORIZATION,
						jwt);

		// response
		mockMvc.perform(request)
		.andExpect(status().is(400))
		//.andExpect(content().json("{restaurants:[{name: Resturant2, rating: 4.3}, {name: Resturant3, rating: 4.6}]}"))
		.andReturn();
	}
	
	@Test
	public void getRestaurantsByRating_empty() throws Exception {
		// list of resturants with rating below, equal and above 4.3
		List<Restaurant> resturants = Arrays.asList();

		RestaurantResponseDto responseDTO = new RestaurantResponseDto(resturants, 1, 10);
		when(restaurantService.findByRating(anyDouble(), anyInt(), anyInt())).thenReturn(responseDTO);

		// request
		RequestBuilder request = MockMvcRequestBuilders
				.get("/restaurants/rating/4.3?pagenumber=1&pagesize=10")
				.accept(MediaType.APPLICATION_JSON)
				.header(HttpHeaders.AUTHORIZATION,
						jwt);

		// response
		mockMvc.perform(request)
		.andExpect(status().is(404))
		//.andExpect(content().json("{restaurants:[{name: Resturant2, rating: 4.3}, {name: Resturant3, rating: 4.6}]}"))
		.andReturn();
	}

	@Test
	public void getRestaurantsByName() throws Exception {
		// list of resturants with rating below, equal and above 4.3
		List<Restaurant> resturants = Arrays.asList(new Restaurant("Resturant1", "Location1", "Chinese", 400, 4.0)
				, new Restaurant("Resturant2", "Location2", "Indian", 200, 4.3)
				, new Restaurant("Resturant3", "Location3", "South Indian", 300, 4.6));

		RestaurantResponseDto responseDTO = new RestaurantResponseDto(resturants, 1, 10);
		when(restaurantService.findByName(any(String.class), anyInt(), anyInt())).thenReturn(responseDTO);

		// request
		RequestBuilder request = MockMvcRequestBuilders
				.get("/restaurants/name/dominos?pagenumber=1&pagesize=10")
				.accept(MediaType.APPLICATION_JSON)
				.header(HttpHeaders.AUTHORIZATION,
						jwt);

		// response
		mockMvc.perform(request)
		.andExpect(status().isOk())
		.andReturn();
	}
	@Test
	public void getRestaurantsByName_zero() throws Exception {
		// list of resturants with rating below, equal and above 4.3
		List<Restaurant> resturants = Arrays.asList(new Restaurant("Resturant1", "Location1", "Chinese", 400, 4.0)
				, new Restaurant("Resturant2", "Location2", "Indian", 200, 4.3)
				, new Restaurant("Resturant3", "Location3", "South Indian", 300, 4.6));

		RestaurantResponseDto responseDTO = new RestaurantResponseDto(resturants, 1, 10);
		when(restaurantService.findByName(any(String.class), anyInt(), anyInt())).thenReturn(responseDTO);

		// request
		RequestBuilder request = MockMvcRequestBuilders
				.get("/restaurants/name/dominos?pagenumber=0&pagesize=10")
				.accept(MediaType.APPLICATION_JSON)
				.header(HttpHeaders.AUTHORIZATION,
						jwt);

		// response
		mockMvc.perform(request)
		.andExpect(status().is(400))
		.andReturn();
	}
	
	@Test
	public void getRestaurantsByName_zeropagesize() throws Exception {
		// list of resturants with rating below, equal and above 4.3
		List<Restaurant> resturants = Arrays.asList(new Restaurant("Resturant1", "Location1", "Chinese", 400, 4.0)
				, new Restaurant("Resturant2", "Location2", "Indian", 200, 4.3)
				, new Restaurant("Resturant3", "Location3", "South Indian", 300, 4.6));

		RestaurantResponseDto responseDTO = new RestaurantResponseDto(resturants, 1, 10);
		when(restaurantService.findByName(any(String.class), anyInt(), anyInt())).thenReturn(responseDTO);

		// request
		RequestBuilder request = MockMvcRequestBuilders
				.get("/restaurants/name/dominos?pagenumber=1&pagesize=0")
				.accept(MediaType.APPLICATION_JSON)
				.header(HttpHeaders.AUTHORIZATION,
						jwt);

		// response
		mockMvc.perform(request)
		.andExpect(status().is(400))
		.andReturn();
	}
	
	
	@Test
	public void getRestaurantsByName_empty() throws Exception {
		// list of resturants with rating below, equal and above 4.3
		List<Restaurant> resturants = Arrays.asList();

		RestaurantResponseDto responseDTO = new RestaurantResponseDto(resturants, 1, 10);
		when(restaurantService.findByName(any(String.class), anyInt(), anyInt())).thenReturn(responseDTO);

		// request
		RequestBuilder request = MockMvcRequestBuilders
				.get("/restaurants/name/dominos?pagenumber=1&pagesize=10")
				.accept(MediaType.APPLICATION_JSON)
				.header(HttpHeaders.AUTHORIZATION,
						jwt);

		// response
		mockMvc.perform(request)
		.andExpect(status().is(404))
		.andReturn();
	}


	@Test
	public void getRestaurantsByLocationAndCuisine() throws Exception {
		// list of resturants with rating below, equal and above 4.3
		List<Restaurant> resturants = Arrays.asList(new Restaurant("Resturant1", "Location1", "Chinese", 400, 4.0)
				, new Restaurant("Resturant2", "Location2", "Indian", 200, 4.3)
				, new Restaurant("Resturant3", "Location3", "South Indian", 300, 4.6));

		RestaurantResponseDto responseDTO = new RestaurantResponseDto(resturants, 1, 10);
		when(restaurantService.findByLocationAndCuisine(any(String.class),any(String.class), anyInt(), anyInt())).thenReturn(responseDTO);

		// request
		RequestBuilder request = MockMvcRequestBuilders
				.get("/restaurants/location/rr/cuisine/italian?pagenumber=1&pagesize=10")
				.accept(MediaType.APPLICATION_JSON)
				.header(HttpHeaders.AUTHORIZATION,
						jwt);

		// response
		mockMvc.perform(request)
		.andExpect(status().isOk())
		.andReturn();
	}

	@Test
	public void getRestaurantsByLocationAndCuisine_zero_pagenumber() throws Exception {
		// list of resturants with rating below, equal and above 4.3
		List<Restaurant> resturants = Arrays.asList(new Restaurant("Resturant1", "Location1", "Chinese", 400, 4.0)
				, new Restaurant("Resturant2", "Location2", "Indian", 200, 4.3)
				, new Restaurant("Resturant3", "Location3", "South Indian", 300, 4.6));

		RestaurantResponseDto responseDTO = new RestaurantResponseDto(resturants, 1, 10);
		when(restaurantService.findByLocationAndCuisine(any(String.class),any(String.class), anyInt(), anyInt())).thenReturn(responseDTO);

		// request
		RequestBuilder request = MockMvcRequestBuilders
				.get("/restaurants/location/rr/cuisine/italian?pagenumber=0&pagesize=10")
				.accept(MediaType.APPLICATION_JSON)
				.header(HttpHeaders.AUTHORIZATION,
						jwt);

		// response
		mockMvc.perform(request)
		.andExpect(status().is(400))
		.andReturn();
	}
	@Test
	public void getRestaurantsByLocationAndCuisine_zero_pagesize() throws Exception {
		// list of resturants with rating below, equal and above 4.3
		List<Restaurant> resturants = Arrays.asList(new Restaurant("Resturant1", "Location1", "Chinese", 400, 4.0)
				, new Restaurant("Resturant2", "Location2", "Indian", 200, 4.3)
				, new Restaurant("Resturant3", "Location3", "South Indian", 300, 4.6));

		RestaurantResponseDto responseDTO = new RestaurantResponseDto(resturants, 1, 10);
		when(restaurantService.findByLocationAndCuisine(any(String.class),any(String.class), anyInt(), anyInt())).thenReturn(responseDTO);

		// request
		RequestBuilder request = MockMvcRequestBuilders
				.get("/restaurants/location/rr/cuisine/italian?pagenumber=1&pagesize=0")
				.accept(MediaType.APPLICATION_JSON)
				.header(HttpHeaders.AUTHORIZATION,
						jwt);

		// response
		mockMvc.perform(request)
		.andExpect(status().is(400))
		.andReturn();
	}



	@Test
	public void getRestaurantsByLocationAndCuisine_empty() throws Exception {
		// list of resturants with rating below, equal and above 4.3
		List<Restaurant> resturants = Arrays.asList();

		RestaurantResponseDto responseDTO = new RestaurantResponseDto(resturants, 1, 10);
		when(restaurantService.findByLocationAndCuisine(any(String.class),any(String.class), anyInt(), anyInt())).thenReturn(responseDTO);

		// request
		RequestBuilder request = MockMvcRequestBuilders
				.get("/restaurants/location/rr/cuisine/italian?pagenumber=1&pagesize=10")
				.accept(MediaType.APPLICATION_JSON)
				.header(HttpHeaders.AUTHORIZATION,
						jwt);

		// response
		mockMvc.perform(request)
		.andExpect(status().is(404))
		.andReturn();
	}



	@Test
	public void getRestaurantsByLocationAndName() throws Exception {
		// list of resturants with rating below, equal and above 4.3
		List<Restaurant> resturants = Arrays.asList(new Restaurant("Resturant1", "Location1", "Chinese", 400, 4.0)
				, new Restaurant("Resturant2", "Location2", "Indian", 200, 4.3)
				, new Restaurant("Resturant3", "Location3", "South Indian", 300, 4.6));

		RestaurantResponseDto responseDTO = new RestaurantResponseDto(resturants, 1, 10);
		when(restaurantService.findByLocationAndName(any(String.class),any(String.class), anyInt(), anyInt())).thenReturn(responseDTO);

		// request
		RequestBuilder request = MockMvcRequestBuilders
				.get("/restaurants/name/dominos/location/rr?pagenumber=1&pagesize=10")
				.accept(MediaType.APPLICATION_JSON)
				.header(HttpHeaders.AUTHORIZATION,
						jwt);

		// response
		mockMvc.perform(request)
		.andExpect(status().isOk())
		.andReturn();
	}
	@Test
	public void getRestaurantsByLocationAndName_bad() throws Exception {
		// list of resturants with rating below, equal and above 4.3
		List<Restaurant> resturants = Arrays.asList(new Restaurant("Resturant1", "Location1", "Chinese", 400, 4.0)
				, new Restaurant("Resturant2", "Location2", "Indian", 200, 4.3)
				, new Restaurant("Resturant3", "Location3", "South Indian", 300, 4.6));

		RestaurantResponseDto responseDTO = new RestaurantResponseDto(resturants, 1, 10);
		when(restaurantService.findByLocationAndName(any(String.class),any(String.class), anyInt(), anyInt())).thenReturn(responseDTO);

		// request
		RequestBuilder request = MockMvcRequestBuilders
				.get("/restaurants/name/dominos/location/rr?pagenumber=0&pagesize=10")
				.accept(MediaType.APPLICATION_JSON).header(HttpHeaders.AUTHORIZATION,
						jwt);

		// response
		mockMvc.perform(request)
		.andExpect(status().is(400))
		.andReturn();
	}
	@Test
	public void getRestaurantsByLocationAndName_noRestaurants() throws Exception {
		// list of resturants with rating below, equal and above 4.3
		List<Restaurant> resturants = Arrays.asList();

		RestaurantResponseDto responseDTO = new RestaurantResponseDto(resturants, 1, 10);
		when(restaurantService.findByLocationAndName(any(String.class),any(String.class), anyInt(), anyInt())).thenReturn(responseDTO);

		// request
		RequestBuilder request = MockMvcRequestBuilders
				.get("/restaurants/name/dominos/location/rr?pagenumber=1&pagesize=10")
				.accept(MediaType.APPLICATION_JSON)
				.header(HttpHeaders.AUTHORIZATION,
						jwt);

		// response
		mockMvc.perform(request)
		.andExpect(status().is(404))
		.andReturn();
	}


	@Test
	public void getRestaurantsByBudget() throws Exception {
		// list of resturants with rating below, equal and above 4.3
		List<Restaurant> resturants = Arrays.asList(new Restaurant("Resturant1", "Location1", "Chinese", 400, 4.0)
				, new Restaurant("Resturant2", "Location2", "Indian", 200, 4.3)
				, new Restaurant("Resturant3", "Location3", "South Indian", 300, 4.6));

		RestaurantResponseDto responseDTO = new RestaurantResponseDto(resturants, 1, 10);
		when(restaurantService.findByBudget(anyInt(), anyInt(), anyInt())).thenReturn(responseDTO);

		// request
		RequestBuilder request = MockMvcRequestBuilders
				.get("/restaurants/budget/400?pagenumber=1&pagesize=10")
				.accept(MediaType.APPLICATION_JSON)
				.header(HttpHeaders.AUTHORIZATION,
						jwt);

		// response
		mockMvc.perform(request)
		.andExpect(status().isOk())
		//.andExpect(content().json("{restaurants:[{name: Resturant2, rating: 4.3}, {name: Resturant3, rating: 4.6}]}"))
		.andReturn();
	}
	@Test
	public void getRestaurantsByBudget_zero() throws Exception {
		// list of resturants with rating below, equal and above 4.3
		List<Restaurant> resturants = Arrays.asList(new Restaurant("Resturant1", "Location1", "Chinese", 400, 4.0)
				, new Restaurant("Resturant2", "Location2", "Indian", 200, 4.3)
				, new Restaurant("Resturant3", "Location3", "South Indian", 300, 4.6));

		RestaurantResponseDto responseDTO = new RestaurantResponseDto(resturants, 1, 10);
		when(restaurantService.findByBudget(anyInt(), anyInt(), anyInt())).thenReturn(responseDTO);

		// request
		RequestBuilder request = MockMvcRequestBuilders
				.get("/restaurants/budget/400?pagenumber=0&pagesize=10")
				.accept(MediaType.APPLICATION_JSON)
				.header(HttpHeaders.AUTHORIZATION,
						jwt);

		// response
		mockMvc.perform(request)
		.andExpect(status().is(400))
		//.andExpect(content().json("{restaurants:[{name: Resturant2, rating: 4.3}, {name: Resturant3, rating: 4.6}]}"))
		.andReturn();
	}

	@Test
	public void getRestaurantsByBudget_empty() throws Exception {
		// list of resturants with rating below, equal and above 4.3
		List<Restaurant> resturants = Arrays.asList();

		RestaurantResponseDto responseDTO = new RestaurantResponseDto(resturants, 1, 10);
		when(restaurantService.findByBudget(anyInt(), anyInt(), anyInt())).thenReturn(responseDTO);

		// request
		RequestBuilder request = MockMvcRequestBuilders
				.get("/restaurants/budget/400?pagenumber=1&pagesize=10")
				.accept(MediaType.APPLICATION_JSON)
				.header(HttpHeaders.AUTHORIZATION,
						jwt);

		// response
		mockMvc.perform(request)
		.andExpect(status().is(404))
		//.andExpect(content().json("{restaurants:[{name: Resturant2, rating: 4.3}, {name: Resturant3, rating: 4.6}]}"))
		.andReturn();
	}

	@Test
	public void getItemsByRestaurantId_basic() throws Exception {
		Menu menu = new Menu("From", "Till", new Restaurant("Resturant2", "Location2", "Indian", 200, 4.3));
		
		// mocking
		when(restaurantService.findMenuItemByRestaurantId(anyLong(), anyInt(), anyInt())).thenReturn(Arrays.asList(
				new MenuItem("Dosa", "Plain Dosa", 200, menu),
				new MenuItem("Khara Bath", "Bath", 200, menu)
				));
		
		// request
		RequestBuilder request = MockMvcRequestBuilders
				.get("/restaurant/items/1?pagenumber=1&pagesize=10")
				.accept(MediaType.APPLICATION_JSON)
				.header(HttpHeaders.AUTHORIZATION,
						jwt);

		// response
		mockMvc.perform(request)
			.andExpect(status().is(200))
			.andReturn();
	}
	
	@Test
	public void getItemsByRestaurantId_empty() throws Exception {
		// mocking
		when(restaurantService.findMenuItemByRestaurantId(anyLong(), anyInt(), anyInt())).thenReturn(Arrays.asList());
		
		// request
		RequestBuilder request = MockMvcRequestBuilders
				.get("/restaurant/items/1?pagenumber=1&pagesize=10")
				.accept(MediaType.APPLICATION_JSON)
				.header(HttpHeaders.AUTHORIZATION,
						jwt);

		// response
		mockMvc.perform(request)
			.andExpect(status().is(404))
			.andReturn();
	}
	
	
	@Test(expected= InvalidTokenException.class)
	public void getItemsByRestaurantId_invalid() throws Exception {
		// mocking
		when(restaurantService.findMenuItemByRestaurantId(anyLong(), anyInt(), anyInt())).thenReturn(Arrays.asList());
		jwt = "Bearer "+Jwts.builder().setSubject("user").claim("roles", "user").setIssuedAt(new Date())
				.signWith(SignatureAlgorithm.HS256, "secretkey").setExpiration(new Date(System.currentTimeMillis() - EXPIRATIONTIME)).compact();
		// request
		RequestBuilder request = MockMvcRequestBuilders
				.get("/restaurant/items/1?pagenumber=1&pagesize=10")
				.accept(MediaType.APPLICATION_JSON)
				.header(HttpHeaders.AUTHORIZATION,
						jwt);

		// response
		mockMvc.perform(request)
			.andExpect(status().is(404))
			.andReturn();
	}
}
