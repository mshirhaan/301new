package com.eatza.restaurantsearch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eatza.restaurantsearch.model.Menu;

@Repository
public interface MenuRepository  extends JpaRepository<Menu, Long>{
	
	Menu findByRestaurant_id( Long restaurantId);
}
