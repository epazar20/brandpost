package com.pazarfy.ws.feed;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pazarfy.ws.user.Users;


@Service(value = "feedSecurity")
public class FeedSecurityService {
	
	@Autowired
	IFeedRepository repo;
	
	public boolean isAllowedToDelete(long id, Users loggedInUser) {
		Optional<Feed> optional = repo.findById(id);
		if(!optional.isPresent()) {
			return false;
		}
		
		Feed feed = optional.get();
		if(feed.getUser().getId() != loggedInUser.getId()) {
			return false;
		}
		
		return true;
	}

}