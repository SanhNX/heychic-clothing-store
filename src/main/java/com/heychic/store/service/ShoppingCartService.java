package com.heychic.store.service;

import com.heychic.store.domain.Product;
import com.heychic.store.domain.CartItem;
import com.heychic.store.domain.ShoppingCart;
import com.heychic.store.domain.User;


public interface ShoppingCartService {

	ShoppingCart getShoppingCart(User user);
	
	int getItemsNumber(User user);
	
	CartItem findCartItemById(Long cartItemId);
	
	CartItem addProductToShoppingCart(Product product, User user, int qty, String size);
		
	void clearShoppingCart(User user);
	
	void updateCartItem(CartItem cartItem, Integer qty);

	void removeCartItem(CartItem cartItem);
	
}
