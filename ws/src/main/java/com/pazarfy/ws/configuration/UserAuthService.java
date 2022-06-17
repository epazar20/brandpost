package com.pazarfy.ws.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.pazarfy.ws.user.IUserService;
import com.pazarfy.ws.user.Users;

@Service
public class UserAuthService implements UserDetailsService {

	@Autowired
	IUserService userService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Users inDB = userService.findByUsername(username);
		if (inDB == null)
			throw new UsernameNotFoundException("User not found");
		return inDB;//new CustomUserDetails(inDB);
	}

}