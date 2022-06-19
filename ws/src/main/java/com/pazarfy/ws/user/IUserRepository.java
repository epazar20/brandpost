package com.pazarfy.ws.user;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserRepository extends JpaRepository<Users, Long> {
	
	Users findByUsername(String username);
	
	Page<Users> findByUsernameNot(String username,Pageable page);

	Page<Users> findByUsertype(int type, Pageable page);

	Page<Users> findByUsertypeAndUsernameNot(int type,String username,Pageable page);

	@Transactional
	void deleteByUsername(String username);

}
