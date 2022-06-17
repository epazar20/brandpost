package com.pazarfy.ws.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserRepository extends JpaRepository<Users, Long> {
	
	Users findByUsername(String username);
	
	Page<Users> findByUsernameNot(String username,Pageable page);

}
