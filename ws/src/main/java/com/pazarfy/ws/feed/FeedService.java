package com.pazarfy.ws.feed;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class FeedService {
	
	@Autowired
	IFeedRepository feedRepo;

	public Feed saveFeed(Feed feed) {
		
		feed.setCreateddate(LocalDateTime.now());
		return feedRepo.save(feed);
		
	}

	public Page<Feed> getFeeds(Pageable page) {
		
		return feedRepo.findAll(page);
	}

}
