package com.heychic.store.service;

import java.util.List;

import com.heychic.store.domain.Order;
import com.heychic.store.domain.Payment;
import com.heychic.store.domain.Shipping;
import com.heychic.store.domain.ShoppingCart;
import com.heychic.store.domain.User;

public interface OrderService {

	Order createOrder(ShoppingCart shoppingCart, Shipping shippingAddress, Payment payment, User customer, User employee);

	List<Order> findByUser(User user);
	
	Order findOrderWithDetails(Long id);
}
