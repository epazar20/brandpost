package com.pazarfy.ws.user;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.pazarfy.ws.dto.UserUpdateDto;

public interface IUserService {
	
	void CreateUser(Users user);

	Users findByUsername(String username);

	Page<Users> getUsers(Pageable page,Users user);

	Users updateByUser(String username, UserUpdateDto data);
}
