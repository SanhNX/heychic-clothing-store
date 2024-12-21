package com.heychic.store.repository;

import com.heychic.store.domain.Product;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.repository.CrudRepository;

import com.heychic.store.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long>{
		
	@EntityGraph(value = "UserComplete", type=EntityGraphType.FETCH)
	User findByUsername(String username);
	Optional<User> findById(Long id);
	User findByEmail(String email);
	List<User> findAllByOrderByIdDesc();
}
