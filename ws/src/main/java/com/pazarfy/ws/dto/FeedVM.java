package com.pazarfy.ws.dto;

import java.sql.Timestamp;
import com.pazarfy.ws.feed.Feed;
import com.pazarfy.ws.user.Users;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FeedVM {

	public FeedVM(Feed feed) {
		this.id = feed.getId();
		this.content = feed.getContent();
		this.createddate = Timestamp.valueOf(feed.getCreateddate()).getTime();
		this.user = new UserVM(feed.getUser());
		if(feed.getFile() != null)
		{
			this.attachment = new FileAttachmentVM();
			this.attachment.setName(feed.getFile().getName());
			this.attachment.setFileType(feed.getFile().getFileType());
		}

	}


	private Long id;

	private String content;

	private Long createddate;

	private UserVM user;
	
	private Long attachmentid;
	
	private FileAttachmentVM attachment;

}
