package com.heychic.store.controller;

import com.heychic.store.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.heychic.store.domain.Address;
import com.heychic.store.domain.Order;
import com.heychic.store.domain.Payment;
import com.heychic.store.domain.Shipping;
import com.heychic.store.domain.ShoppingCart;
import com.heychic.store.domain.User;
import com.heychic.store.service.OrderService;
import com.heychic.store.service.ShoppingCartService;

@Controller
public class CheckoutControler {
	
	@Autowired 
	private ShoppingCartService shoppingCartService;
	
	@Autowired
	private OrderService orderService;

	@Autowired
	private UserService userService;

	@RequestMapping("/checkout")
	public String checkout(@RequestParam(value = "missingRequiredField", required = false) boolean missingRequiredField,
						   Model model, Authentication authentication) {
		User user = (User) authentication.getPrincipal();
		ShoppingCart shoppingCart = shoppingCartService.getShoppingCart(user);
		if (shoppingCart.isEmpty()) {
			model.addAttribute("emptyCart", true);
			return "redirect:/shopping-cart/cart";
		}
		model.addAttribute("cartItemList", shoppingCart.getCartItems());
		model.addAttribute("shoppingCart", shoppingCart);

		// Add all customers for employee selection
		model.addAttribute("customerList", userService.findAllByRoleId(1));

		if (missingRequiredField) {
			model.addAttribute("missingRequiredField", true);
		}
		return "checkout";
	}

	@RequestMapping(value = "/checkout", method = RequestMethod.POST)
	public String placeOrder(@ModelAttribute("shipping") Shipping shipping,
							 @ModelAttribute("address") Address address,
							 @ModelAttribute("payment") Payment payment,
							 @RequestParam(value = "customerCheckout", required = false) Long customerId,
							 RedirectAttributes redirectAttributes, Authentication authentication) {

		User user = (User) authentication.getPrincipal();
		User customer = customerId != null ? userService.findById(customerId) : user;

		ShoppingCart shoppingCart = shoppingCartService.getShoppingCart(user);

		if (!shoppingCart.isEmpty()) {
			shipping.setAddress(address);
			Order order = orderService.createOrder(shoppingCart, shipping, payment, customer, customerId != null ? user : null);
			redirectAttributes.addFlashAttribute("order", order);
		}
		return "redirect:/order-submitted";
	}

	@RequestMapping(value = "/order-submitted", method = RequestMethod.GET)
	public String orderSubmitted(Model model) {
		Order order = (Order) model.asMap().get("order");
		if (order == null) {
			return "redirect:/";
		}
		model.addAttribute("order", order);
		return "orderSubmitted";
	}

}
