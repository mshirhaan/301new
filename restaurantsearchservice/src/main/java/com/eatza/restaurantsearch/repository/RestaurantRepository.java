
package com.eatza.restaurantsearch.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eatza.restaurantsearch.model.Restaurant;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
	
	
	Page<Restaurant> findAll(Pageable pageable);
	Page<Restaurant> findByNameContaining(String name, Pageable pageable);
	Page<Restaurant> findByRatingGreaterThanEqual(double rating, Pageable pageable);
	Optional<Restaurant> findById(Long id);
	Page<Restaurant> findByLocationContainingAndCuisineContaining(String location, String cuisine, Pageable pageable);
	Page<Restaurant> findByLocationContainingOrCuisineContaining(String location, String cuisine, Pageable pageable);
	Page<Restaurant> findByBudgetLessThanEqual(int budget, Pageable pageable);
	Page<Restaurant> findByLocationContainingAndNameContaining(String location, String name, Pageable pageable);
	Page<Restaurant> findByLocationContainingOrNameContaining(String location, String name, Pageable pageable);
	

}
