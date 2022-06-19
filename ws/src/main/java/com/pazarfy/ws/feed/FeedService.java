package com.pazarfy.ws.feed;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

import com.pazarfy.ws.dto.FeedVM;
import com.pazarfy.ws.file.FileAttachment;
import com.pazarfy.ws.file.FileRepository;
import com.pazarfy.ws.file.FileService;
import com.pazarfy.ws.user.UserService;
import com.pazarfy.ws.user.Users;

@Service
public class FeedService {

	@Autowired
	IFeedRepository feedRepo;

	@Autowired
	UserService userService;

	@Autowired
	FileRepository filerepo;
	
	@Autowired
    FileService fileservice;

	public Feed saveFeed(FeedVM feedvm, Users user) {

		Feed feed = new Feed();
		feed.setContent(feedvm.getContent());
		feed.setCreateddate(LocalDateTime.now());
		feed.setUser(user);
		Feed result = feedRepo.save(feed);
		if (feedvm.getAttachmentid() != null) {
			Optional<FileAttachment> optFile = filerepo.findById(feedvm.getAttachmentid());
			if (optFile.isPresent()) {
				FileAttachment file = optFile.get();
				file.setFeed(result);
				filerepo.save(file);
			}
		}

		return result;

	}

	public Page<Feed> getFeeds(Pageable page) {

		return feedRepo.findAll(page);
	}

	public Page<Feed> getFeedsOfUser(String username, Pageable page) {

		Users user = userService.findByUsername(username);

		return feedRepo.findByUser(user, page);
	}

	public Page<Feed> findById(long id, String isbefore, Pageable page) {
		if (isbefore.equals("1")) {
			// id ile gelindiğinde her zaman id öncesini pagele
			Pageable pageBefore = PageRequest.of(0, page.getPageSize(), page.getSort());
			return feedRepo.findByIdLessThan(id, pageBefore);

		}
		// yeni oluşanlar için hepsi gelsin //max size ile
		Pageable pageAfter = PageRequest.of(0, 500, page.getSort());
		return feedRepo.findByIdGreaterThan(id, pageAfter);

	}

	public Page<Feed> findByIdAndUsername(long id, String isbefore, String username, Pageable page) {

		Users user = userService.findByUsername(username);
		if (isbefore.equals("1")) {
			// id ile gelindiğinde her zaman id öncesini pagele
			Pageable pageBefore = PageRequest.of(0, page.getPageSize(), page.getSort());
			return feedRepo.findByIdLessThanAndUser(id, user, pageBefore);

		}
		// yeni oluşanlar için hepsi gelsin //max size ile
		Pageable pageAfter = PageRequest.of(0, 500, page.getSort());
		return feedRepo.findByIdGreaterThanAndUser(id, user, pageAfter);
	}

	public int countByIdUsername(long id, String username) {
		Users user = userService.findByUsername(username);
		return feedRepo.countByIdGreaterThanAndUser(id, user);
	}

	public int countById(long id) {
		return feedRepo.countByIdGreaterThan(id);
	}

	
	public void delete(long id) {
		Feed inDB = feedRepo.getOne(id);
		if(inDB.getFile() != null) {
			String fileName = inDB.getFile().getName();
			fileservice.deleteAttachmentFile(fileName);
		}
		feedRepo.deleteById(id);
	}

}
