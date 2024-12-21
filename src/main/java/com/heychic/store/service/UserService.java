package com.heychic.store.service;


import java.util.List;
import java.util.Optional;

import com.heychic.store.domain.Category;
import com.heychic.store.domain.User;
import com.heychic.store.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

public interface UserService {
	
	User findById(Long id);
	User findByUsername(String username);
	User findByEmail(String email);
	void save(User user);
	User createUser(String username, String email,  String password, List<String> roles);
	List<User> findAll();
	void deleteById(Long id);
}
