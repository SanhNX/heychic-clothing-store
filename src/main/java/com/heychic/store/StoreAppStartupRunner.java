package com.heychic.store;

import java.util.Arrays;

import com.heychic.store.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class StoreAppStartupRunner implements CommandLineRunner{

	@Autowired
	private UserService userService;
	
	@Override
	public void run(String... args) throws Exception {
		userService.createUser("admin", "123456", "admin@admin.com", Arrays.asList("ROLE_USER", "ROLE_ADMIN"));
	}
}

