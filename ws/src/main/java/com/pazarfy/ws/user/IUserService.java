package com.pazarfy.ws.user;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.pazarfy.ws.dto.UserUpdateVM;


public interface IUserService {
	
	void CreateUser(Users user);

	Users findByUsername(String username);

	Page<Users> getUsers(Pageable page,Users user,int type);

	Users updateByUser(String username, UserUpdateVM data);

	void delete(String username);
}
