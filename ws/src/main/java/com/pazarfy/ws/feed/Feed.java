package com.pazarfy.ws.feed;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.Size;

import com.pazarfy.ws.file.FileAttachment;
import com.pazarfy.ws.user.Users;

import lombok.Data;

@Entity
@Data
public class Feed {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	
	@Size(min=4,max=1000,message = "{pazarfy.feed.validation.constraints.Size.message}")
	@Column(length = 1000)
	String content;
	
	
	LocalDateTime createddate;
	
	@ManyToOne
	Users user;
	
	@OneToOne(mappedBy = "feed",cascade = CascadeType.REMOVE)
	FileAttachment file;

}
