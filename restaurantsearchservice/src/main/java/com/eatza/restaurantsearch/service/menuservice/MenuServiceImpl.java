package com.eatza.restaurantsearch.service.menuservice;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.eatza.restaurantsearch.model.Menu;
import com.eatza.restaurantsearch.repository.MenuRepository;

@Service
public class MenuServiceImpl implements MenuService {

	private static final Logger logger = LoggerFactory.getLogger(MenuServiceImpl.class);

	@Autowired
	private MenuRepository menuRepository;

	@Override
	public Menu saveMenu(Menu menu) {
		logger.debug("In save menu method, calling repo");
		return menuRepository.save(menu);
	}

	@Override
	@Cacheable(value="menubyid")
	public Optional<Menu> getMenuById(Long id) {
		logger.debug("In get menu by ID method, calling repo");
		return menuRepository.findById(id);
	}

	@Override
	@Cacheable(value="menusbyrestaurantis")
	public Menu getMenuByRestaurantId(Long id) {
		logger.debug("In get menu by restaurant id method, calling repo");
		return menuRepository.findByRestaurant_id(id);
	}
}
