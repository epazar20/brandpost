package com.pazarfy.ws.dto;

import com.pazarfy.ws.user.Users;

import lombok.Data;

@Data
public class UserVM {
	
	String username;
	
	String displayname;
	
	String image;

	public UserVM(Users user ){
		
		username = user.getUsername();
		displayname= user.getDisplayname();
		image= user.getImage();
		
	}
	
	

}
