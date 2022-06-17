package com.pazarfy.ws.feed;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.pazarfy.ws.shared.GenericResponse;

@RestController
@RequestMapping("/api/1.0")
public class FeedController {
	
	FeedService feedService;

	@Autowired
	public FeedController(FeedService feedService) {
		super();
		this.feedService = feedService;
	}
	
	@PostMapping("/feeds")
	public GenericResponse saveFeed(@Valid @RequestBody Feed feed){
		
		Feed result = feedService.saveFeed(feed);
		return new GenericResponse(result.getContent());
	}
	
	
	@GetMapping("/feeds")
	public Page<Feed> getFeeds(@PageableDefault(sort = "id",direction = Direction.DESC) Pageable page)
	{
		
		return feedService.getFeeds(page);
		
	}

}
