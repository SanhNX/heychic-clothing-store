package com.heychic.store.service;

import java.util.List;

import com.heychic.store.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {

	List<Product> findAllProducts();
	
	Page<Product> findProductsByCriteria(Pageable pageable, Integer priceLow, Integer priceHigh, List<String> sizes,
										 List<String> categories, List<String> brands, String search);
		
	List<Product> findFirstProducts();

	Product findProductById(Long id);
	
	Product saveProduct(Product product);

	void deleteProductById(Long id);
	
	List<String> getAllSizes();

	List<String> getAllCategories();

	List<String> getAllBrands();

}
