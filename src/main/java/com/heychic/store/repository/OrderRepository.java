package com.heychic.store.repository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;

import com.heychic.store.domain.Order;
import com.heychic.store.domain.User;

public interface OrderRepository extends CrudRepository<Order, Long> {

	List<Order> findByUser(User user); 
	
	@EntityGraph(attributePaths = { "cartItems", "payment", "shipping" })
	Order findEagerById(Long id);

}
