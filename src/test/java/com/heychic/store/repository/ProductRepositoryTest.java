package com.heychic.store.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import com.heychic.store.domain.Product;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.heychic.store.domain.ProductBuilder;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ProductRepositoryTest {
	
	@Autowired
	private TestEntityManager entityManager;
	 
	@Autowired
	private ProductRepository repository;
		 	
	@Before
	public void setUp() {
		Product product = new ProductBuilder()
				.withTitle("product1")
				.withPrice(50)
				.sizesAvailable(Arrays.asList("38","39"))
				.ofCategories(Arrays.asList("running"))
				.ofBrand(Arrays.asList("nike"))
				.build();	
        entityManager.persist(product);
        
        Product product2 = new ProductBuilder()
				.withTitle("product2")
				.withPrice(100)
				.sizesAvailable(Arrays.asList("39", "40"))
				.ofCategories(Arrays.asList("urban"))
				.ofBrand(Arrays.asList("nike"))
				.build();     
        entityManager.persist(product2);
        
        Product product3 = new ProductBuilder()
				.withTitle("product3")
				.withPrice(200)
				.sizesAvailable(Arrays.asList("39", "45", "43"))
				.ofCategories(Arrays.asList("sneaker"))
				.ofBrand(Arrays.asList("adidas"))
				.build();     
        entityManager.persist(product3);
        
        Product product4 = new ProductBuilder()
				.withTitle("product4")
				.sizesAvailable(Arrays.asList("45", "38"))
				.ofBrand(Arrays.asList("puma"))
				.withPrice(300)
				.build();     
        entityManager.persist(product4);
        
        Product product5 = new ProductBuilder()
				.withTitle("product5")
				.withPrice(500)
				.ofBrand(Arrays.asList("puma"))
				.build();     
        entityManager.persist(product5);
	}
		
	@Test
	public void should_find_all_distinct_sizes() {		
        assertThat(repository.findAllSizes()).hasSize(5).contains("38","39","40","43","45");        
	}
	
	@Test
	public void should_find_all_distinct_brands() {		
        assertThat(repository.findAllBrands()).hasSize(3).contains("nike", "adidas", "puma");        
	}
	
	@Test
	public void should_find_all_distinct_categories() {		
        assertThat(repository.findAllCategories()).hasSize(3).contains("running", "urban", "sneaker");	
	}
	
	@Test
	public void should_filter_products_between_prices() {
		int low = 100;
		int high = 300;
		List<Product> results = repository.findAll(ProductSpecification.filterBy(low, high, null, null, null, null));
		assertThat(results).hasSize(3).extracting("title").contains("product2", "product3", "product4");
	}
	
	@Test
	public void should_filter_products_with_price_higher_than_number() {
		int low = 300;
		List<Product> results = repository.findAll(ProductSpecification.filterBy(low, null, null, null, null, null));
		assertThat(results).hasSize(2).extracting("title").contains("product4", "product5");
	}
	
	@Test
	public void should_filter_products_with_price_lower_than_number() {
		int high = 200;
		List<Product> results = repository.findAll(ProductSpecification.filterBy(null, high, null, null, null, null));
		assertThat(results).hasSize(3).extracting("title").contains("product1", "product2", "product3");
	}
	
	@Test
	public void should_filter_products_by_size() {
		List<Product> results = repository.findAll(ProductSpecification.filterBy(null, null, Arrays.asList("40", "45"), null, null, null));
		List<Product> results2 = repository.findAll(ProductSpecification.filterBy(null, null, Arrays.asList("39"), null, null, null));
		assertThat(results).hasSize(3).extracting("title").contains("product2", "product3", "product4");
		assertThat(results2).hasSize(3).extracting("title").contains("product1", "product2", "product3");
	}
	
	@Test
	public void should_filter_products_by_category() {
		List<Product> results = repository.findAll(ProductSpecification.filterBy(null, null, null, Arrays.asList("running", "urban"), null, null));
		List<Product> results2 = repository.findAll(ProductSpecification.filterBy(null, null, null, Arrays.asList("sneaker", "notARealCategory"), null, null));
		assertThat(results).hasSize(2).extracting("title").contains("product1", "product2");
		assertThat(results2).hasSize(1).extracting("title").contains("product3");
	}
	
	@Test
	public void should_filter_products_by_brand() {
		List<Product> results = repository.findAll(ProductSpecification.filterBy(null, null, null, null, Arrays.asList("nike"), null));
		List<Product> results2 = repository.findAll(ProductSpecification.filterBy(null, null, null, null, Arrays.asList("adidas", "notARealCategory"), null));
		assertThat(results).hasSize(2).extracting("title").contains("product1", "product2");
		assertThat(results2).hasSize(1).extracting("title").contains("product3");
	}
	
	@Test
	public void should_filter_products_by_search_term() {
		List<Product> results = repository.findAll(ProductSpecification.filterBy(null, null, null, null, null, "product"));
		List<Product> results2 = repository.findAll(ProductSpecification.filterBy(null, null, null, null, null, "cle4"));
		List<Product> results3 = repository.findAll(ProductSpecification.filterBy(null, null, null, null, null, "unmatchingterm"));
		assertThat(results).hasSize(5).extracting("title").contains("product1", "product2", "product3", "product4", "product5");
		assertThat(results2).hasSize(1).extracting("title").contains("product4");
		assertThat(results3).isEmpty();
	}
	
	@Test
	public void should_find_all_if_all_filters_are_null() {
		List<Product> results = repository.findAll(ProductSpecification.filterBy(null, null, null, null, null, null));
		assertThat(results).hasSize(5).extracting("title").contains("product1", "product2", "product3", "product4", "product5");
	}

}
