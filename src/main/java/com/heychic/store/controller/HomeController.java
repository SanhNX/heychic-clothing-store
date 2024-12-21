package com.heychic.store.controller;

import java.util.List;

import com.heychic.store.domain.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.heychic.store.service.ProductService;

@Controller
public class HomeController {
		
	@Autowired
	private ProductService productService;
	
	
	@RequestMapping("/")
	public String index(Model model) {		
		List<Product> products = productService.findFirstProducts();
		model.addAttribute("products", products);
		return "index";
	}

	
}
