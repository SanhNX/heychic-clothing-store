package com.heychic.store.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.heychic.store.domain.User;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long>{
		
	@EntityGraph(value = "UserComplete", type=EntityGraphType.FETCH)
	User findByUsername(String username);
	Optional<User> findById(Long id);
	User findByEmail(String email);
	List<User> findAllByOrderByIdDesc();
	@Query(value = "SELECT u.* FROM user u INNER JOIN user_role ur ON u.id = ur.user_id WHERE ur.role_id = :roleId", nativeQuery = true)
	List<User> findAllByRoleId(@Param("roleId") Integer roleId);
}
