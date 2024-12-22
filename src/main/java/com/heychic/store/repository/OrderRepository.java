package com.heychic.store.repository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.Query;


import com.heychic.store.domain.Order;
import com.heychic.store.domain.User;
import org.springframework.data.repository.query.Param;

public interface OrderRepository extends CrudRepository<Order, Long> {

	List<Order> findByUser(User user); 
	
	@EntityGraph(attributePaths = { "cartItems", "payment", "shipping" })
	Order findEagerById(Long id);

	@Query(value = "SELECT SUM(o.order_total) FROM user_order o WHERE o.employee_id = :employeeId", nativeQuery = true)
	Double calculateRevenueByEmployee(@Param("employeeId") Long employeeId);
}
