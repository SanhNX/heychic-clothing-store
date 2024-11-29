package com.heychic.store.repository;

import org.springframework.data.repository.CrudRepository;

import com.heychic.store.domain.security.Role;

public interface RoleRepository extends CrudRepository<Role, Long> {
	
	Role findByName(String name);

}
