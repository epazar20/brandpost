package com.pazarfy.ws.dto;

import com.pazarfy.ws.user.Users;

import lombok.Data;

@Data
public class UserDto {
	
	String username;
	
	String displayname;
	
	String image;

	public UserDto(Users user ){
		
		username = user.getUsername();
		displayname= user.getDisplayname();
		image= user.getImage();
		
	}
	
	

}
