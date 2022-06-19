package com.pazarfy.ws.file;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pazarfy.ws.feed.Feed;
import com.pazarfy.ws.user.Users;


public interface FileRepository extends JpaRepository<FileAttachment, Long> {

	List<FileAttachment> findByDateBeforeAndFeedIsNull(Date date);

	List<FileAttachment> findByFeed(Feed feed);

	List<FileAttachment> findByFeedUser(Users inDb);

}
