package com.pazarfy.ws.feed;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.pazarfy.ws.dto.FeedVM;
import com.pazarfy.ws.file.FileAttachment;
import com.pazarfy.ws.file.FileRepository;
import com.pazarfy.ws.shared.CurrentUser;
import com.pazarfy.ws.shared.GenericResponse;
import com.pazarfy.ws.user.Users;

@RestController
@RequestMapping("/api/1.0")
public class FeedController {

	FeedService feedService;

	FileRepository fileRepo;

	@Autowired
	public FeedController(FeedService feedService, FileRepository fileRepo) {
		super();
		this.feedService = feedService;
		this.fileRepo = fileRepo;
	}

	@PostMapping("/feeds")
	public GenericResponse saveFeed(@Valid @RequestBody FeedVM feed, @CurrentUser Users user) {
		Feed result = feedService.saveFeed(feed, user);
		return new GenericResponse(result.getContent());
	}

	@GetMapping("/feeds")
	public Page<FeedVM> getFeeds(@RequestParam(required = false, defaultValue = "0") long id,
			@RequestParam(required = false, defaultValue = "1") String isbefore,
			@RequestParam(required = false) String username,
			@PageableDefault(sort = "id", direction = Direction.DESC) Pageable page) {

		if (username != null && !username.isEmpty()) {
			if (id > 0) {
				return feedService.findByIdAndUsername(id, isbefore, username, page).map(FeedVM::new);

			}
			return feedService.getFeedsOfUser(username, page).map(FeedVM::new);
		}

		if (id > 0) {
			return feedService.findById(id, isbefore, page).map(FeedVM::new);

		}
		return feedService.getFeeds(page).map(FeedVM::new);

	}

	@GetMapping("/new/feeds/count")
	public Map<String, Integer> getFeedsCount(@RequestParam(required = false) String username,
			@RequestParam(required = false, defaultValue = "0") long id) {

		Map<String, Integer> hashMap = new HashMap<String, Integer>();
		if (username != null && !username.isEmpty()) {
			if (id > 0) {
				hashMap.put("count", feedService.countByIdUsername(id, username));
				return hashMap;

			}
		}

		hashMap.put("count", feedService.countById(id));
		return hashMap;

	}
	
	
	@DeleteMapping("/feeds/{id:[0-9]+}")
	@PreAuthorize("@feedSecurity.isAllowedToDelete(#id, principal)")
	GenericResponse deleteHoax(@PathVariable long id) {
		feedService.delete(id);
		return new GenericResponse("Feed removed");
	}

}
