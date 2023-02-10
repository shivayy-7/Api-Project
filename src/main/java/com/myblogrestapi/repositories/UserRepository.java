package com.myblogrestapi.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myblogrestapi.entity.User;
public interface UserRepository extends JpaRepository<User, Long>{

	Optional<User> findByEmail(String email);
	
	Optional<User> findByUsernameOrEmail(String username, String email);
	
	Optional<User> findByUsername(String username);
	
//	Boolean existByUsername(String username);
//	
//	Boolean existByEmail(String email);
}
